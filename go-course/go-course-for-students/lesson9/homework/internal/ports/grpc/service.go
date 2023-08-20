package grpc

import (
	"context"
	"errors"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
	"google.golang.org/protobuf/types/known/emptypb"
	"google.golang.org/protobuf/types/known/timestamppb"
	"homework9/internal/app"
)

type Service struct {
	a app.App
}

func (d Service) CreateAd(_ context.Context, request *CreateAdRequest) (*AdResponse, error) {
	ad, err := d.a.CreateAd(request.Title, request.Text, request.UserId)
	if err != nil {
		if errors.Is(err, app.ValidationError{}.Err) {
			return &AdResponse{}, status.Error(codes.InvalidArgument, err.Error())
		}
		return &AdResponse{}, status.Error(codes.Internal, err.Error())
	}
	return &AdResponse{Id: ad.ID,
		Title:        ad.Title,
		Text:         ad.Text,
		AuthorId:     ad.AuthorID,
		Published:    ad.Published,
		CreationDate: timestamppb.New(ad.CreatedDate),
		UpdateDate:   timestamppb.New(ad.CreatedDate)}, nil
}

func (d Service) ChangeAdStatus(_ context.Context, request *ChangeAdStatusRequest) (*AdResponse, error) {
	ad, err := d.a.ChangeAdStatus(request.AdId, request.UserId, request.Published)
	if err != nil {
		if errors.Is(err, app.PermissionError{}.Err) {
			return &AdResponse{}, status.Error(codes.PermissionDenied, err.Error())
		}
		if errors.Is(err, app.ValidationError{}.Err) {
			return &AdResponse{}, status.Error(codes.InvalidArgument, err.Error())
		}
		return &AdResponse{}, status.Error(codes.Internal, err.Error())
	}
	return &AdResponse{Id: ad.ID,
		Title:        ad.Title,
		Text:         ad.Text,
		AuthorId:     ad.AuthorID,
		Published:    ad.Published,
		CreationDate: timestamppb.New(ad.CreatedDate),
		UpdateDate:   timestamppb.New(ad.CreatedDate)}, nil
}

func (d Service) UpdateAd(_ context.Context, request *UpdateAdRequest) (*AdResponse, error) {
	ad, err := d.a.UpdateAd(request.AdId, request.UserId, request.Title, request.Text)
	if err != nil {
		if errors.Is(err, app.PermissionError{}.Err) {
			return &AdResponse{}, status.Error(codes.PermissionDenied, err.Error())
		}
		if errors.Is(err, app.ValidationError{}.Err) {
			return &AdResponse{}, status.Error(codes.InvalidArgument, err.Error())
		}
		return &AdResponse{}, status.Error(codes.Internal, err.Error())
	}
	return &AdResponse{Id: ad.ID,
		Title:        ad.Title,
		Text:         ad.Text,
		AuthorId:     ad.AuthorID,
		Published:    ad.Published,
		CreationDate: timestamppb.New(ad.CreatedDate),
		UpdateDate:   timestamppb.New(ad.CreatedDate)}, nil
}

func (d Service) CreateUser(_ context.Context, request *User) (*User, error) {
	u, err := d.a.CreateUser(request.Nickname, request.Email, request.UserId)
	if err != nil {
		if errors.Is(err, app.ValidationError{}.Err) {
			return &User{}, status.Error(codes.InvalidArgument, err.Error())
		}
		return &User{}, status.Error(codes.Internal, err.Error())
	}
	return &User{UserId: u.UserID, Nickname: u.Nickname, Email: u.Email}, nil
}

func (d Service) GetUser(_ context.Context, request *GetUserRequest) (*User, error) {
	u, err := d.a.GetUserByID(request.Id)
	if err != nil {
		return &User{}, status.Error(codes.Internal, err.Error())
	}

	return &User{UserId: u.UserID, Nickname: u.Nickname, Email: u.Email}, nil
}

func (d Service) DeleteUserByID(_ context.Context, request *DeleteUserRequest) (*emptypb.Empty, error) {
	err := d.a.DeleteUserByID(request.Id)
	if err != nil {
		if errors.Is(err, app.ValidationError{}.Err) {
			return &emptypb.Empty{}, status.Error(codes.InvalidArgument, err.Error())
		}
		return &emptypb.Empty{}, status.Error(codes.Internal, err.Error())
	}
	return &emptypb.Empty{}, nil
}

func (d Service) DeleteAdByAuthorID(_ context.Context, request *DeleteAdRequest) (*emptypb.Empty, error) {
	err := d.a.DeleteAdByAuthorID(request.AuthorId)
	if err != nil {
		if errors.Is(err, app.PermissionError{}.Err) {
			return &emptypb.Empty{}, status.Error(codes.PermissionDenied, err.Error())
		}
		if errors.Is(err, app.ValidationError{}.Err) {
			return &emptypb.Empty{}, status.Error(codes.InvalidArgument, err.Error())
		}
		return &emptypb.Empty{}, status.Error(codes.Internal, err.Error())
	}
	return &emptypb.Empty{}, nil
}

func (d Service) GetAdByID(_ context.Context, request *GetAdRequest) (*AdResponse, error) {
	ad, err := d.a.GetAdByID(request.Id)
	if err != nil {
		if errors.Is(err, app.ValidationError{}.Err) {
			return &AdResponse{}, status.Error(codes.InvalidArgument, err.Error())
		}
		return &AdResponse{}, status.Error(codes.Internal, err.Error())
	}
	return &AdResponse{Id: ad.ID,
		Title:        ad.Title,
		Text:         ad.Text,
		AuthorId:     ad.AuthorID,
		Published:    ad.Published,
		CreationDate: timestamppb.New(ad.CreatedDate),
		UpdateDate:   timestamppb.New(ad.CreatedDate)}, nil
}

func NewService(a app.App) *Service {
	return &Service{a}
}
