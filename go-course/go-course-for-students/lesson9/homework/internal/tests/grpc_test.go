package tests

import (
	"context"
	"google.golang.org/grpc/credentials/insecure"
	"homework9/internal/adapters/userrepo"
	"net"
	"testing"
	"time"

	"github.com/stretchr/testify/assert"
	"google.golang.org/grpc"
	"google.golang.org/grpc/test/bufconn"
	"homework9/internal/adapters/adrepo"
	"homework9/internal/app"
	grpcPort "homework9/internal/ports/grpc"
)

func GetClient(t *testing.T) (grpcPort.AdServiceClient, context.Context) {
	lis := bufconn.Listen(1024 * 1024)
	t.Cleanup(func() {
		err := lis.Close()
		if err != nil {
			return
		}
	})

	srv := grpc.NewServer(grpc.ChainUnaryInterceptor( /*grpcPort.UnaryInterceptor, grpcPort.RecoveryInterceptor*/ ))
	t.Cleanup(func() {
		srv.Stop()
	})

	svc := grpcPort.NewService(app.NewApp(adrepo.New(), userrepo.New()))
	grpcPort.RegisterAdServiceServer(srv, svc)

	go func() {
		assert.NoError(t, srv.Serve(lis), "srv.Serve")
	}()

	dialer := func(context.Context, string) (net.Conn, error) {
		return lis.Dial()
	}

	ctx, cancel := context.WithTimeout(context.Background(), 30*time.Second)
	t.Cleanup(func() {
		cancel()
	})

	conn, err := grpc.DialContext(ctx, "", grpc.WithContextDialer(dialer),
		grpc.WithTransportCredentials(insecure.NewCredentials()))
	assert.NoError(t, err, "grpc.DialContext")

	t.Cleanup(func() {
		err := conn.Close()
		if err != nil {
			return
		}
	})

	client := grpcPort.NewAdServiceClient(conn)
	return client, ctx
}

func TestGRPCCreateUser(t *testing.T) {
	client, ctx := GetClient(t)

	res, err := client.CreateUser(ctx, &grpcPort.User{Nickname: "Tom", Email: "example@mail.com", UserId: 3})
	assert.NoError(t, err, "client.CreateUser")
	assert.Equal(t, "Tom", res.Nickname)
	assert.Equal(t, "example@mail.com", res.Email)
	assert.Equal(t, int64(3), res.UserId)
	res, err = client.CreateUser(ctx, &grpcPort.User{Nickname: "qwerty", Email: "qwerty@mail.com", UserId: 5})
	assert.NoError(t, err, "client.CreateUser")
	assert.Equal(t, "qwerty", res.Nickname)
	assert.Equal(t, "qwerty@mail.com", res.Email)
	assert.Equal(t, int64(5), res.UserId)
}
