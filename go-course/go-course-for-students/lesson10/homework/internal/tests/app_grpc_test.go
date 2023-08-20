package tests

import (
	"context"
	"github.com/stretchr/testify/assert"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
	"google.golang.org/grpc/test/bufconn"
	"homework10/internal/adapters/adrepo"
	"homework10/internal/adapters/userrepo"
	"homework10/internal/app"
	grpcPorts "homework10/internal/ports/grpc"
	"net"
	"testing"
	"time"
)

func GetClient(t *testing.T) (grpcPorts.AdServiceClient, context.Context) {
	lis := bufconn.Listen(1024 * 1024)
	t.Cleanup(func() {
		err := lis.Close()
		if err != nil {
			return
		}
	})

	srv := grpc.NewServer(grpc.ChainUnaryInterceptor(grpcPorts.PanicInterceptor, grpcPorts.LoggerInterceptor))
	t.Cleanup(func() {
		srv.Stop()
	})

	svc := grpcPorts.NewService(app.NewApp(adrepo.New(), userrepo.New()))
	grpcPorts.RegisterAdServiceServer(srv, svc)

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

	client := grpcPorts.NewAdServiceClient(conn)
	return client, ctx
}

func TestGRPCCreateUser(t *testing.T) {
	client, ctx := GetClient(t)

	res, err := client.CreateUser(ctx, &grpcPorts.User{Nickname: "Tom", Email: "example@mail.com", UserId: 3})
	assert.NoError(t, err, "client.CreateUser")
	assert.Equal(t, "Tom", res.Nickname)
	assert.Equal(t, "example@mail.com", res.Email)
	assert.Equal(t, int64(3), res.UserId)
	res, err = client.CreateUser(ctx, &grpcPorts.User{Nickname: "qwerty", Email: "qwerty@mail.com", UserId: 5})
	assert.NoError(t, err, "client.CreateUser")
	assert.Equal(t, "qwerty", res.Nickname)
	assert.Equal(t, "qwerty@mail.com", res.Email)
	assert.Equal(t, int64(5), res.UserId)
}

func TestGRPCCreateAd(t *testing.T) {
	client, ctx := GetClient(t)
	res, err := client.CreateAd(ctx, &grpcPorts.CreateAdRequest{Title: "title", Text: "text", UserId: 15})
	assert.NoError(t, err, "client.CreateAd")
	assert.Equal(t, "title", res.Title)
	assert.Equal(t, "text", res.Text)
	assert.Equal(t, int64(15), res.AuthorId)
	res, err = client.CreateAd(ctx, &grpcPorts.CreateAdRequest{Title: "title1", Text: "text1", UserId: 17})
	assert.NoError(t, err, "client.CreateAd")
	assert.Equal(t, "title1", res.Title)
	assert.Equal(t, "text1", res.Text)
	assert.Equal(t, int64(17), res.AuthorId)
}

func TestGRPCChangeAdStatus(t *testing.T) {
	client, ctx := GetClient(t)
	resp, err := client.CreateAd(ctx, &grpcPorts.CreateAdRequest{Title: "title", Text: "text", UserId: 15})
	assert.NoError(t, err, "client.CreateAd")
	res, err := client.ChangeAdStatus(ctx, &grpcPorts.ChangeAdStatusRequest{AdId: resp.Id, UserId: resp.AuthorId, Published: true})
	assert.NoError(t, err, "client.ChangeStatus")
	assert.Equal(t, true, res.Published)
}

func TestGRPCUpdateAd(t *testing.T) {
	client, ctx := GetClient(t)
	resp, err := client.CreateAd(ctx, &grpcPorts.CreateAdRequest{Title: "title", Text: "text", UserId: 15})
	assert.NoError(t, err, "client.CreateAd")
	res, err := client.UpdateAd(ctx, &grpcPorts.UpdateAdRequest{AdId: resp.Id, Title: "newTitle", Text: "newText", UserId: resp.AuthorId})
	assert.NoError(t, err, "client.UpdateAd")
	assert.Equal(t, "newTitle", res.Title)
	assert.Equal(t, "newText", res.Text)
}

func TestGRPCGetUserByID(t *testing.T) {
	client, ctx := GetClient(t)
	resp, err := client.CreateUser(ctx, &grpcPorts.User{Nickname: "Tom", Email: "example@mail.com", UserId: 3})
	assert.NoError(t, err, "client.CreateUser")
	res, err := client.GetUser(ctx, &grpcPorts.GetUserRequest{Id: 3})
	assert.NoError(t, err, "client.GetUserByID")
	assert.Equal(t, resp.Nickname, res.Nickname)
	assert.Equal(t, resp.Email, res.Email)
}

func TestGRPCDeleteUserByID(t *testing.T) {
	client, ctx := GetClient(t)
	_, err := client.CreateUser(ctx, &grpcPorts.User{Nickname: "Tom", Email: "example@mail.com", UserId: 3})
	assert.NoError(t, err, "client.CreateUser")
	_, err = client.DeleteUserByID(ctx, &grpcPorts.DeleteUserRequest{Id: 3})
	assert.NoError(t, err, "client.DeleteUserByID")
}

func TestGRPCDeleteAdByAuthorID(t *testing.T) {
	client, ctx := GetClient(t)
	resp, err := client.CreateAd(ctx, &grpcPorts.CreateAdRequest{Title: "title", Text: "text", UserId: 15})
	assert.NoError(t, err, "client.CreateAd")
	_, err = client.DeleteAdByAuthorID(ctx, &grpcPorts.DeleteAdRequest{AuthorId: 15, AdId: resp.GetId()})
	assert.NoError(t, err, "client.DeleteAdByAuthorID")
}

func TestGRPCGetAdByID(t *testing.T) {
	client, ctx := GetClient(t)
	resp, err := client.CreateAd(ctx, &grpcPorts.CreateAdRequest{Title: "title", Text: "text", UserId: 15})
	assert.NoError(t, err, "client.CreateAd")
	res, err := client.GetAdByID(ctx, &grpcPorts.GetAdRequest{Id: resp.GetId()})
	assert.NoError(t, err, "client.GetAdByID")
	assert.Equal(t, resp.AuthorId, res.AuthorId)
	assert.Equal(t, resp.Text, res.Text)
	assert.Equal(t, resp.Title, res.Title)
}
