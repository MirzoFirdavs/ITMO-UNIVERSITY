package main

import (
	"context"
	"errors"
	"fmt"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
	"homework9/internal/adapters/adrepo"
	"homework9/internal/adapters/userrepo"
	"homework9/internal/app"
	"homework9/internal/ports/httpgin"
	"log"
	"net"
	"net/http"
	"os"
	"os/signal"
	"runtime/debug"
	"syscall"
	"time"

	"golang.org/x/sync/errgroup"
	"google.golang.org/grpc"
	grpcPorts "homework9/internal/ports/grpc"
)

const (
	grpcPort = ":8080"
	httpPort = ":18080"
)
const httpShutdownTime = 30 * time.Second

func main() {
	lis, err := net.Listen("tcp", grpcPort)
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}

	grpcServer := grpc.NewServer(grpc.ChainUnaryInterceptor(LoggerInterceptor, PanicInterceptor))
	grpcPorts.RegisterAdServiceServer(grpcServer, grpcPorts.NewService(app.NewApp(adrepo.New(), userrepo.New())))
	httpServer := httpgin.NewHTTPServer(httpPort, app.NewApp(adrepo.New(), userrepo.New()))

	eg, ctx := errgroup.WithContext(context.Background())

	sigQuit := make(chan os.Signal, 1)
	signal.Ignore(syscall.SIGHUP, syscall.SIGPIPE)
	signal.Notify(sigQuit, syscall.SIGINT, syscall.SIGTERM)

	eg.Go(func() error {
		select {
		case s := <-sigQuit:
			log.Printf("captured signal: %v\n", s)
			return fmt.Errorf("captured signal: %v", s)
		case <-ctx.Done():
			return nil
		}
	})

	eg.Go(func() error {
		log.Printf("starting grpc server, listening on %s\n", grpcPort)
		defer log.Printf("close grpc server listening on %s\n", grpcPort)

		errCh := make(chan error)

		defer func() {
			grpcServer.GracefulStop()
			_ = lis.Close()

			close(errCh)
		}()

		go func() {
			if err := grpcServer.Serve(lis); err != nil {
				errCh <- err
			}
		}()

		select {
		case <-ctx.Done():
			return ctx.Err()
		case err := <-errCh:
			return fmt.Errorf("grpc server can't listen and serve requests: %w", err)
		}
	})

	eg.Go(func() error {
		log.Printf("starting http server, listening on %s\n", httpServer.Addr)
		defer log.Printf("close http server listening on %s\n", httpServer.Addr)

		errCh := make(chan error)

		defer func() {
			shCtx, cancel := context.WithTimeout(context.Background(), httpShutdownTime)
			defer cancel()

			if err := httpServer.Shutdown(shCtx); err != nil {
				log.Printf("can't close http server listening on %s: %s", httpServer.Addr, err.Error())
			}

			close(errCh)
		}()

		go func() {
			if err := httpServer.ListenAndServe(); !errors.Is(err, http.ErrServerClosed) {
				errCh <- err
			}
		}()

		select {
		case <-ctx.Done():
			return ctx.Err()
		case err := <-errCh:
			return fmt.Errorf("http server can't listen and serve requests: %w", err)
		}
	})

	if err := eg.Wait(); err != nil {
		log.Printf("gracefully shutting down the servers: %s\n", err.Error())
	}

	log.Println("servers were successfully shutdown")
}

func LoggerInterceptor(ctx context.Context, req interface{}, info *grpc.UnaryServerInfo, handler grpc.UnaryHandler) (interface{}, error) {
	start := time.Now()

	res, err := handler(ctx, req)

	log.Println("full method:", info.FullMethod, "since:", time.Since(start), "error:", err)
	return res, err
}

func PanicInterceptor(ctx context.Context, req interface{}, info *grpc.UnaryServerInfo, handler grpc.UnaryHandler) (_ interface{}, err error) {
	defer func() {
		if r := recover(); r != nil {
			err = status.Error(codes.Internal, fmt.Sprintf("Panic: `%s` %s", info.FullMethod, string(debug.Stack())))
		}
	}()
	return handler(ctx, req)
}
