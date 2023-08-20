package grpc

import (
	"context"
	"fmt"
	"google.golang.org/grpc"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
	"log"
	"runtime/debug"
	"time"
)

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
