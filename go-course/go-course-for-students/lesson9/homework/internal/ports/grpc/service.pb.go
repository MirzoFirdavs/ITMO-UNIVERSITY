// Code generated by protoc-gen-go. DO NOT EDIT.
// versions:
// 	protoc-gen-go v1.28.1
// 	protoc        v4.22.3
// source: lesson9/homework/internal/ports/grpc/service.proto

package grpc

import (
	protoreflect "google.golang.org/protobuf/reflect/protoreflect"
	protoimpl "google.golang.org/protobuf/runtime/protoimpl"
	emptypb "google.golang.org/protobuf/types/known/emptypb"
	timestamppb "google.golang.org/protobuf/types/known/timestamppb"
	reflect "reflect"
	sync "sync"
)

const (
	// Verify that this generated code is sufficiently up-to-date.
	_ = protoimpl.EnforceVersion(20 - protoimpl.MinVersion)
	// Verify that runtime/protoimpl is sufficiently up-to-date.
	_ = protoimpl.EnforceVersion(protoimpl.MaxVersion - 20)
)

type CreateAdRequest struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Title  string `protobuf:"bytes,1,opt,name=title,proto3" json:"title,omitempty"`
	Text   string `protobuf:"bytes,2,opt,name=text,proto3" json:"text,omitempty"`
	UserId int64  `protobuf:"varint,3,opt,name=user_id,json=userId,proto3" json:"user_id,omitempty"`
}

func (x *CreateAdRequest) Reset() {
	*x = CreateAdRequest{}
	if protoimpl.UnsafeEnabled {
		mi := &file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[0]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *CreateAdRequest) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*CreateAdRequest) ProtoMessage() {}

func (x *CreateAdRequest) ProtoReflect() protoreflect.Message {
	mi := &file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[0]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use CreateAdRequest.ProtoReflect.Descriptor instead.
func (*CreateAdRequest) Descriptor() ([]byte, []int) {
	return file_lesson9_homework_internal_ports_grpc_service_proto_rawDescGZIP(), []int{0}
}

func (x *CreateAdRequest) GetTitle() string {
	if x != nil {
		return x.Title
	}
	return ""
}

func (x *CreateAdRequest) GetText() string {
	if x != nil {
		return x.Text
	}
	return ""
}

func (x *CreateAdRequest) GetUserId() int64 {
	if x != nil {
		return x.UserId
	}
	return 0
}

type ChangeAdStatusRequest struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	AdId      int64 `protobuf:"varint,1,opt,name=ad_id,json=adId,proto3" json:"ad_id,omitempty"`
	UserId    int64 `protobuf:"varint,2,opt,name=user_id,json=userId,proto3" json:"user_id,omitempty"`
	Published bool  `protobuf:"varint,3,opt,name=published,proto3" json:"published,omitempty"`
}

func (x *ChangeAdStatusRequest) Reset() {
	*x = ChangeAdStatusRequest{}
	if protoimpl.UnsafeEnabled {
		mi := &file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[1]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *ChangeAdStatusRequest) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*ChangeAdStatusRequest) ProtoMessage() {}

func (x *ChangeAdStatusRequest) ProtoReflect() protoreflect.Message {
	mi := &file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[1]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use ChangeAdStatusRequest.ProtoReflect.Descriptor instead.
func (*ChangeAdStatusRequest) Descriptor() ([]byte, []int) {
	return file_lesson9_homework_internal_ports_grpc_service_proto_rawDescGZIP(), []int{1}
}

func (x *ChangeAdStatusRequest) GetAdId() int64 {
	if x != nil {
		return x.AdId
	}
	return 0
}

func (x *ChangeAdStatusRequest) GetUserId() int64 {
	if x != nil {
		return x.UserId
	}
	return 0
}

func (x *ChangeAdStatusRequest) GetPublished() bool {
	if x != nil {
		return x.Published
	}
	return false
}

type UpdateAdRequest struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	AdId   int64  `protobuf:"varint,1,opt,name=ad_id,json=adId,proto3" json:"ad_id,omitempty"`
	Title  string `protobuf:"bytes,2,opt,name=title,proto3" json:"title,omitempty"`
	Text   string `protobuf:"bytes,3,opt,name=text,proto3" json:"text,omitempty"`
	UserId int64  `protobuf:"varint,4,opt,name=user_id,json=userId,proto3" json:"user_id,omitempty"`
}

func (x *UpdateAdRequest) Reset() {
	*x = UpdateAdRequest{}
	if protoimpl.UnsafeEnabled {
		mi := &file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[2]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *UpdateAdRequest) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*UpdateAdRequest) ProtoMessage() {}

func (x *UpdateAdRequest) ProtoReflect() protoreflect.Message {
	mi := &file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[2]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use UpdateAdRequest.ProtoReflect.Descriptor instead.
func (*UpdateAdRequest) Descriptor() ([]byte, []int) {
	return file_lesson9_homework_internal_ports_grpc_service_proto_rawDescGZIP(), []int{2}
}

func (x *UpdateAdRequest) GetAdId() int64 {
	if x != nil {
		return x.AdId
	}
	return 0
}

func (x *UpdateAdRequest) GetTitle() string {
	if x != nil {
		return x.Title
	}
	return ""
}

func (x *UpdateAdRequest) GetText() string {
	if x != nil {
		return x.Text
	}
	return ""
}

func (x *UpdateAdRequest) GetUserId() int64 {
	if x != nil {
		return x.UserId
	}
	return 0
}

type AdResponse struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Id           int64                  `protobuf:"varint,1,opt,name=id,proto3" json:"id,omitempty"`
	Title        string                 `protobuf:"bytes,2,opt,name=title,proto3" json:"title,omitempty"`
	Text         string                 `protobuf:"bytes,3,opt,name=text,proto3" json:"text,omitempty"`
	AuthorId     int64                  `protobuf:"varint,4,opt,name=author_id,json=authorId,proto3" json:"author_id,omitempty"`
	Published    bool                   `protobuf:"varint,5,opt,name=published,proto3" json:"published,omitempty"`
	CreationDate *timestamppb.Timestamp `protobuf:"bytes,6,opt,name=creation_date,json=creationDate,proto3" json:"creation_date,omitempty"`
	UpdateDate   *timestamppb.Timestamp `protobuf:"bytes,7,opt,name=update_date,json=updateDate,proto3" json:"update_date,omitempty"`
}

func (x *AdResponse) Reset() {
	*x = AdResponse{}
	if protoimpl.UnsafeEnabled {
		mi := &file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[3]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *AdResponse) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*AdResponse) ProtoMessage() {}

func (x *AdResponse) ProtoReflect() protoreflect.Message {
	mi := &file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[3]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use AdResponse.ProtoReflect.Descriptor instead.
func (*AdResponse) Descriptor() ([]byte, []int) {
	return file_lesson9_homework_internal_ports_grpc_service_proto_rawDescGZIP(), []int{3}
}

func (x *AdResponse) GetId() int64 {
	if x != nil {
		return x.Id
	}
	return 0
}

func (x *AdResponse) GetTitle() string {
	if x != nil {
		return x.Title
	}
	return ""
}

func (x *AdResponse) GetText() string {
	if x != nil {
		return x.Text
	}
	return ""
}

func (x *AdResponse) GetAuthorId() int64 {
	if x != nil {
		return x.AuthorId
	}
	return 0
}

func (x *AdResponse) GetPublished() bool {
	if x != nil {
		return x.Published
	}
	return false
}

func (x *AdResponse) GetCreationDate() *timestamppb.Timestamp {
	if x != nil {
		return x.CreationDate
	}
	return nil
}

func (x *AdResponse) GetUpdateDate() *timestamppb.Timestamp {
	if x != nil {
		return x.UpdateDate
	}
	return nil
}

type User struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Nickname string `protobuf:"bytes,1,opt,name=nickname,proto3" json:"nickname,omitempty"`
	Email    string `protobuf:"bytes,2,opt,name=email,proto3" json:"email,omitempty"`
	UserId   int64  `protobuf:"varint,3,opt,name=user_id,json=userId,proto3" json:"user_id,omitempty"`
}

func (x *User) Reset() {
	*x = User{}
	if protoimpl.UnsafeEnabled {
		mi := &file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[4]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *User) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*User) ProtoMessage() {}

func (x *User) ProtoReflect() protoreflect.Message {
	mi := &file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[4]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use User.ProtoReflect.Descriptor instead.
func (*User) Descriptor() ([]byte, []int) {
	return file_lesson9_homework_internal_ports_grpc_service_proto_rawDescGZIP(), []int{4}
}

func (x *User) GetNickname() string {
	if x != nil {
		return x.Nickname
	}
	return ""
}

func (x *User) GetEmail() string {
	if x != nil {
		return x.Email
	}
	return ""
}

func (x *User) GetUserId() int64 {
	if x != nil {
		return x.UserId
	}
	return 0
}

type GetUserRequest struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Id int64 `protobuf:"varint,1,opt,name=id,proto3" json:"id,omitempty"`
}

func (x *GetUserRequest) Reset() {
	*x = GetUserRequest{}
	if protoimpl.UnsafeEnabled {
		mi := &file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[5]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *GetUserRequest) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*GetUserRequest) ProtoMessage() {}

func (x *GetUserRequest) ProtoReflect() protoreflect.Message {
	mi := &file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[5]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use GetUserRequest.ProtoReflect.Descriptor instead.
func (*GetUserRequest) Descriptor() ([]byte, []int) {
	return file_lesson9_homework_internal_ports_grpc_service_proto_rawDescGZIP(), []int{5}
}

func (x *GetUserRequest) GetId() int64 {
	if x != nil {
		return x.Id
	}
	return 0
}

type GetAdRequest struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Id int64 `protobuf:"varint,1,opt,name=id,proto3" json:"id,omitempty"`
}

func (x *GetAdRequest) Reset() {
	*x = GetAdRequest{}
	if protoimpl.UnsafeEnabled {
		mi := &file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[6]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *GetAdRequest) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*GetAdRequest) ProtoMessage() {}

func (x *GetAdRequest) ProtoReflect() protoreflect.Message {
	mi := &file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[6]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use GetAdRequest.ProtoReflect.Descriptor instead.
func (*GetAdRequest) Descriptor() ([]byte, []int) {
	return file_lesson9_homework_internal_ports_grpc_service_proto_rawDescGZIP(), []int{6}
}

func (x *GetAdRequest) GetId() int64 {
	if x != nil {
		return x.Id
	}
	return 0
}

type DeleteUserRequest struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Id int64 `protobuf:"varint,1,opt,name=id,proto3" json:"id,omitempty"`
}

func (x *DeleteUserRequest) Reset() {
	*x = DeleteUserRequest{}
	if protoimpl.UnsafeEnabled {
		mi := &file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[7]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *DeleteUserRequest) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*DeleteUserRequest) ProtoMessage() {}

func (x *DeleteUserRequest) ProtoReflect() protoreflect.Message {
	mi := &file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[7]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use DeleteUserRequest.ProtoReflect.Descriptor instead.
func (*DeleteUserRequest) Descriptor() ([]byte, []int) {
	return file_lesson9_homework_internal_ports_grpc_service_proto_rawDescGZIP(), []int{7}
}

func (x *DeleteUserRequest) GetId() int64 {
	if x != nil {
		return x.Id
	}
	return 0
}

type DeleteAdRequest struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	AdId     int64 `protobuf:"varint,1,opt,name=ad_id,json=adId,proto3" json:"ad_id,omitempty"`
	AuthorId int64 `protobuf:"varint,2,opt,name=author_id,json=authorId,proto3" json:"author_id,omitempty"`
}

func (x *DeleteAdRequest) Reset() {
	*x = DeleteAdRequest{}
	if protoimpl.UnsafeEnabled {
		mi := &file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[8]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *DeleteAdRequest) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*DeleteAdRequest) ProtoMessage() {}

func (x *DeleteAdRequest) ProtoReflect() protoreflect.Message {
	mi := &file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[8]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use DeleteAdRequest.ProtoReflect.Descriptor instead.
func (*DeleteAdRequest) Descriptor() ([]byte, []int) {
	return file_lesson9_homework_internal_ports_grpc_service_proto_rawDescGZIP(), []int{8}
}

func (x *DeleteAdRequest) GetAdId() int64 {
	if x != nil {
		return x.AdId
	}
	return 0
}

func (x *DeleteAdRequest) GetAuthorId() int64 {
	if x != nil {
		return x.AuthorId
	}
	return 0
}

var File_lesson9_homework_internal_ports_grpc_service_proto protoreflect.FileDescriptor

var file_lesson9_homework_internal_ports_grpc_service_proto_rawDesc = []byte{
	0x0a, 0x32, 0x6c, 0x65, 0x73, 0x73, 0x6f, 0x6e, 0x39, 0x2f, 0x68, 0x6f, 0x6d, 0x65, 0x77, 0x6f,
	0x72, 0x6b, 0x2f, 0x69, 0x6e, 0x74, 0x65, 0x72, 0x6e, 0x61, 0x6c, 0x2f, 0x70, 0x6f, 0x72, 0x74,
	0x73, 0x2f, 0x67, 0x72, 0x70, 0x63, 0x2f, 0x73, 0x65, 0x72, 0x76, 0x69, 0x63, 0x65, 0x2e, 0x70,
	0x72, 0x6f, 0x74, 0x6f, 0x12, 0x02, 0x61, 0x64, 0x1a, 0x1b, 0x67, 0x6f, 0x6f, 0x67, 0x6c, 0x65,
	0x2f, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x62, 0x75, 0x66, 0x2f, 0x65, 0x6d, 0x70, 0x74, 0x79, 0x2e,
	0x70, 0x72, 0x6f, 0x74, 0x6f, 0x1a, 0x1f, 0x67, 0x6f, 0x6f, 0x67, 0x6c, 0x65, 0x2f, 0x70, 0x72,
	0x6f, 0x74, 0x6f, 0x62, 0x75, 0x66, 0x2f, 0x74, 0x69, 0x6d, 0x65, 0x73, 0x74, 0x61, 0x6d, 0x70,
	0x2e, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x22, 0x54, 0x0a, 0x0f, 0x43, 0x72, 0x65, 0x61, 0x74, 0x65,
	0x41, 0x64, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, 0x12, 0x14, 0x0a, 0x05, 0x74, 0x69, 0x74,
	0x6c, 0x65, 0x18, 0x01, 0x20, 0x01, 0x28, 0x09, 0x52, 0x05, 0x74, 0x69, 0x74, 0x6c, 0x65, 0x12,
	0x12, 0x0a, 0x04, 0x74, 0x65, 0x78, 0x74, 0x18, 0x02, 0x20, 0x01, 0x28, 0x09, 0x52, 0x04, 0x74,
	0x65, 0x78, 0x74, 0x12, 0x17, 0x0a, 0x07, 0x75, 0x73, 0x65, 0x72, 0x5f, 0x69, 0x64, 0x18, 0x03,
	0x20, 0x01, 0x28, 0x03, 0x52, 0x06, 0x75, 0x73, 0x65, 0x72, 0x49, 0x64, 0x22, 0x63, 0x0a, 0x15,
	0x43, 0x68, 0x61, 0x6e, 0x67, 0x65, 0x41, 0x64, 0x53, 0x74, 0x61, 0x74, 0x75, 0x73, 0x52, 0x65,
	0x71, 0x75, 0x65, 0x73, 0x74, 0x12, 0x13, 0x0a, 0x05, 0x61, 0x64, 0x5f, 0x69, 0x64, 0x18, 0x01,
	0x20, 0x01, 0x28, 0x03, 0x52, 0x04, 0x61, 0x64, 0x49, 0x64, 0x12, 0x17, 0x0a, 0x07, 0x75, 0x73,
	0x65, 0x72, 0x5f, 0x69, 0x64, 0x18, 0x02, 0x20, 0x01, 0x28, 0x03, 0x52, 0x06, 0x75, 0x73, 0x65,
	0x72, 0x49, 0x64, 0x12, 0x1c, 0x0a, 0x09, 0x70, 0x75, 0x62, 0x6c, 0x69, 0x73, 0x68, 0x65, 0x64,
	0x18, 0x03, 0x20, 0x01, 0x28, 0x08, 0x52, 0x09, 0x70, 0x75, 0x62, 0x6c, 0x69, 0x73, 0x68, 0x65,
	0x64, 0x22, 0x69, 0x0a, 0x0f, 0x55, 0x70, 0x64, 0x61, 0x74, 0x65, 0x41, 0x64, 0x52, 0x65, 0x71,
	0x75, 0x65, 0x73, 0x74, 0x12, 0x13, 0x0a, 0x05, 0x61, 0x64, 0x5f, 0x69, 0x64, 0x18, 0x01, 0x20,
	0x01, 0x28, 0x03, 0x52, 0x04, 0x61, 0x64, 0x49, 0x64, 0x12, 0x14, 0x0a, 0x05, 0x74, 0x69, 0x74,
	0x6c, 0x65, 0x18, 0x02, 0x20, 0x01, 0x28, 0x09, 0x52, 0x05, 0x74, 0x69, 0x74, 0x6c, 0x65, 0x12,
	0x12, 0x0a, 0x04, 0x74, 0x65, 0x78, 0x74, 0x18, 0x03, 0x20, 0x01, 0x28, 0x09, 0x52, 0x04, 0x74,
	0x65, 0x78, 0x74, 0x12, 0x17, 0x0a, 0x07, 0x75, 0x73, 0x65, 0x72, 0x5f, 0x69, 0x64, 0x18, 0x04,
	0x20, 0x01, 0x28, 0x03, 0x52, 0x06, 0x75, 0x73, 0x65, 0x72, 0x49, 0x64, 0x22, 0xff, 0x01, 0x0a,
	0x0a, 0x41, 0x64, 0x52, 0x65, 0x73, 0x70, 0x6f, 0x6e, 0x73, 0x65, 0x12, 0x0e, 0x0a, 0x02, 0x69,
	0x64, 0x18, 0x01, 0x20, 0x01, 0x28, 0x03, 0x52, 0x02, 0x69, 0x64, 0x12, 0x14, 0x0a, 0x05, 0x74,
	0x69, 0x74, 0x6c, 0x65, 0x18, 0x02, 0x20, 0x01, 0x28, 0x09, 0x52, 0x05, 0x74, 0x69, 0x74, 0x6c,
	0x65, 0x12, 0x12, 0x0a, 0x04, 0x74, 0x65, 0x78, 0x74, 0x18, 0x03, 0x20, 0x01, 0x28, 0x09, 0x52,
	0x04, 0x74, 0x65, 0x78, 0x74, 0x12, 0x1b, 0x0a, 0x09, 0x61, 0x75, 0x74, 0x68, 0x6f, 0x72, 0x5f,
	0x69, 0x64, 0x18, 0x04, 0x20, 0x01, 0x28, 0x03, 0x52, 0x08, 0x61, 0x75, 0x74, 0x68, 0x6f, 0x72,
	0x49, 0x64, 0x12, 0x1c, 0x0a, 0x09, 0x70, 0x75, 0x62, 0x6c, 0x69, 0x73, 0x68, 0x65, 0x64, 0x18,
	0x05, 0x20, 0x01, 0x28, 0x08, 0x52, 0x09, 0x70, 0x75, 0x62, 0x6c, 0x69, 0x73, 0x68, 0x65, 0x64,
	0x12, 0x3f, 0x0a, 0x0d, 0x63, 0x72, 0x65, 0x61, 0x74, 0x69, 0x6f, 0x6e, 0x5f, 0x64, 0x61, 0x74,
	0x65, 0x18, 0x06, 0x20, 0x01, 0x28, 0x0b, 0x32, 0x1a, 0x2e, 0x67, 0x6f, 0x6f, 0x67, 0x6c, 0x65,
	0x2e, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x62, 0x75, 0x66, 0x2e, 0x54, 0x69, 0x6d, 0x65, 0x73, 0x74,
	0x61, 0x6d, 0x70, 0x52, 0x0c, 0x63, 0x72, 0x65, 0x61, 0x74, 0x69, 0x6f, 0x6e, 0x44, 0x61, 0x74,
	0x65, 0x12, 0x3b, 0x0a, 0x0b, 0x75, 0x70, 0x64, 0x61, 0x74, 0x65, 0x5f, 0x64, 0x61, 0x74, 0x65,
	0x18, 0x07, 0x20, 0x01, 0x28, 0x0b, 0x32, 0x1a, 0x2e, 0x67, 0x6f, 0x6f, 0x67, 0x6c, 0x65, 0x2e,
	0x70, 0x72, 0x6f, 0x74, 0x6f, 0x62, 0x75, 0x66, 0x2e, 0x54, 0x69, 0x6d, 0x65, 0x73, 0x74, 0x61,
	0x6d, 0x70, 0x52, 0x0a, 0x75, 0x70, 0x64, 0x61, 0x74, 0x65, 0x44, 0x61, 0x74, 0x65, 0x22, 0x51,
	0x0a, 0x04, 0x55, 0x73, 0x65, 0x72, 0x12, 0x1a, 0x0a, 0x08, 0x6e, 0x69, 0x63, 0x6b, 0x6e, 0x61,
	0x6d, 0x65, 0x18, 0x01, 0x20, 0x01, 0x28, 0x09, 0x52, 0x08, 0x6e, 0x69, 0x63, 0x6b, 0x6e, 0x61,
	0x6d, 0x65, 0x12, 0x14, 0x0a, 0x05, 0x65, 0x6d, 0x61, 0x69, 0x6c, 0x18, 0x02, 0x20, 0x01, 0x28,
	0x09, 0x52, 0x05, 0x65, 0x6d, 0x61, 0x69, 0x6c, 0x12, 0x17, 0x0a, 0x07, 0x75, 0x73, 0x65, 0x72,
	0x5f, 0x69, 0x64, 0x18, 0x03, 0x20, 0x01, 0x28, 0x03, 0x52, 0x06, 0x75, 0x73, 0x65, 0x72, 0x49,
	0x64, 0x22, 0x20, 0x0a, 0x0e, 0x47, 0x65, 0x74, 0x55, 0x73, 0x65, 0x72, 0x52, 0x65, 0x71, 0x75,
	0x65, 0x73, 0x74, 0x12, 0x0e, 0x0a, 0x02, 0x69, 0x64, 0x18, 0x01, 0x20, 0x01, 0x28, 0x03, 0x52,
	0x02, 0x69, 0x64, 0x22, 0x1e, 0x0a, 0x0c, 0x47, 0x65, 0x74, 0x41, 0x64, 0x52, 0x65, 0x71, 0x75,
	0x65, 0x73, 0x74, 0x12, 0x0e, 0x0a, 0x02, 0x69, 0x64, 0x18, 0x01, 0x20, 0x01, 0x28, 0x03, 0x52,
	0x02, 0x69, 0x64, 0x22, 0x23, 0x0a, 0x11, 0x44, 0x65, 0x6c, 0x65, 0x74, 0x65, 0x55, 0x73, 0x65,
	0x72, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, 0x12, 0x0e, 0x0a, 0x02, 0x69, 0x64, 0x18, 0x01,
	0x20, 0x01, 0x28, 0x03, 0x52, 0x02, 0x69, 0x64, 0x22, 0x43, 0x0a, 0x0f, 0x44, 0x65, 0x6c, 0x65,
	0x74, 0x65, 0x41, 0x64, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, 0x12, 0x13, 0x0a, 0x05, 0x61,
	0x64, 0x5f, 0x69, 0x64, 0x18, 0x01, 0x20, 0x01, 0x28, 0x03, 0x52, 0x04, 0x61, 0x64, 0x49, 0x64,
	0x12, 0x1b, 0x0a, 0x09, 0x61, 0x75, 0x74, 0x68, 0x6f, 0x72, 0x5f, 0x69, 0x64, 0x18, 0x02, 0x20,
	0x01, 0x28, 0x03, 0x52, 0x08, 0x61, 0x75, 0x74, 0x68, 0x6f, 0x72, 0x49, 0x64, 0x32, 0xb8, 0x03,
	0x0a, 0x09, 0x41, 0x64, 0x53, 0x65, 0x72, 0x76, 0x69, 0x63, 0x65, 0x12, 0x31, 0x0a, 0x08, 0x43,
	0x72, 0x65, 0x61, 0x74, 0x65, 0x41, 0x64, 0x12, 0x13, 0x2e, 0x61, 0x64, 0x2e, 0x43, 0x72, 0x65,
	0x61, 0x74, 0x65, 0x41, 0x64, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, 0x1a, 0x0e, 0x2e, 0x61,
	0x64, 0x2e, 0x41, 0x64, 0x52, 0x65, 0x73, 0x70, 0x6f, 0x6e, 0x73, 0x65, 0x22, 0x00, 0x12, 0x3d,
	0x0a, 0x0e, 0x43, 0x68, 0x61, 0x6e, 0x67, 0x65, 0x41, 0x64, 0x53, 0x74, 0x61, 0x74, 0x75, 0x73,
	0x12, 0x19, 0x2e, 0x61, 0x64, 0x2e, 0x43, 0x68, 0x61, 0x6e, 0x67, 0x65, 0x41, 0x64, 0x53, 0x74,
	0x61, 0x74, 0x75, 0x73, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, 0x1a, 0x0e, 0x2e, 0x61, 0x64,
	0x2e, 0x41, 0x64, 0x52, 0x65, 0x73, 0x70, 0x6f, 0x6e, 0x73, 0x65, 0x22, 0x00, 0x12, 0x31, 0x0a,
	0x08, 0x55, 0x70, 0x64, 0x61, 0x74, 0x65, 0x41, 0x64, 0x12, 0x13, 0x2e, 0x61, 0x64, 0x2e, 0x55,
	0x70, 0x64, 0x61, 0x74, 0x65, 0x41, 0x64, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, 0x1a, 0x0e,
	0x2e, 0x61, 0x64, 0x2e, 0x41, 0x64, 0x52, 0x65, 0x73, 0x70, 0x6f, 0x6e, 0x73, 0x65, 0x22, 0x00,
	0x12, 0x2f, 0x0a, 0x09, 0x47, 0x65, 0x74, 0x41, 0x64, 0x42, 0x79, 0x49, 0x44, 0x12, 0x10, 0x2e,
	0x61, 0x64, 0x2e, 0x47, 0x65, 0x74, 0x41, 0x64, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, 0x1a,
	0x0e, 0x2e, 0x61, 0x64, 0x2e, 0x41, 0x64, 0x52, 0x65, 0x73, 0x70, 0x6f, 0x6e, 0x73, 0x65, 0x22,
	0x00, 0x12, 0x22, 0x0a, 0x0a, 0x43, 0x72, 0x65, 0x61, 0x74, 0x65, 0x55, 0x73, 0x65, 0x72, 0x12,
	0x08, 0x2e, 0x61, 0x64, 0x2e, 0x55, 0x73, 0x65, 0x72, 0x1a, 0x08, 0x2e, 0x61, 0x64, 0x2e, 0x55,
	0x73, 0x65, 0x72, 0x22, 0x00, 0x12, 0x29, 0x0a, 0x07, 0x47, 0x65, 0x74, 0x55, 0x73, 0x65, 0x72,
	0x12, 0x12, 0x2e, 0x61, 0x64, 0x2e, 0x47, 0x65, 0x74, 0x55, 0x73, 0x65, 0x72, 0x52, 0x65, 0x71,
	0x75, 0x65, 0x73, 0x74, 0x1a, 0x08, 0x2e, 0x61, 0x64, 0x2e, 0x55, 0x73, 0x65, 0x72, 0x22, 0x00,
	0x12, 0x41, 0x0a, 0x0e, 0x44, 0x65, 0x6c, 0x65, 0x74, 0x65, 0x55, 0x73, 0x65, 0x72, 0x42, 0x79,
	0x49, 0x44, 0x12, 0x15, 0x2e, 0x61, 0x64, 0x2e, 0x44, 0x65, 0x6c, 0x65, 0x74, 0x65, 0x55, 0x73,
	0x65, 0x72, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, 0x1a, 0x16, 0x2e, 0x67, 0x6f, 0x6f, 0x67,
	0x6c, 0x65, 0x2e, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x62, 0x75, 0x66, 0x2e, 0x45, 0x6d, 0x70, 0x74,
	0x79, 0x22, 0x00, 0x12, 0x43, 0x0a, 0x12, 0x44, 0x65, 0x6c, 0x65, 0x74, 0x65, 0x41, 0x64, 0x42,
	0x79, 0x41, 0x75, 0x74, 0x68, 0x6f, 0x72, 0x49, 0x44, 0x12, 0x13, 0x2e, 0x61, 0x64, 0x2e, 0x44,
	0x65, 0x6c, 0x65, 0x74, 0x65, 0x41, 0x64, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, 0x1a, 0x16,
	0x2e, 0x67, 0x6f, 0x6f, 0x67, 0x6c, 0x65, 0x2e, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x62, 0x75, 0x66,
	0x2e, 0x45, 0x6d, 0x70, 0x74, 0x79, 0x22, 0x00, 0x42, 0x26, 0x5a, 0x24, 0x6c, 0x65, 0x73, 0x73,
	0x6f, 0x6e, 0x39, 0x2f, 0x68, 0x6f, 0x6d, 0x65, 0x77, 0x6f, 0x72, 0x6b, 0x2f, 0x69, 0x6e, 0x74,
	0x65, 0x72, 0x6e, 0x61, 0x6c, 0x2f, 0x70, 0x6f, 0x72, 0x74, 0x73, 0x2f, 0x67, 0x72, 0x70, 0x63,
	0x62, 0x06, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x33,
}

var (
	file_lesson9_homework_internal_ports_grpc_service_proto_rawDescOnce sync.Once
	file_lesson9_homework_internal_ports_grpc_service_proto_rawDescData = file_lesson9_homework_internal_ports_grpc_service_proto_rawDesc
)

func file_lesson9_homework_internal_ports_grpc_service_proto_rawDescGZIP() []byte {
	file_lesson9_homework_internal_ports_grpc_service_proto_rawDescOnce.Do(func() {
		file_lesson9_homework_internal_ports_grpc_service_proto_rawDescData = protoimpl.X.CompressGZIP(file_lesson9_homework_internal_ports_grpc_service_proto_rawDescData)
	})
	return file_lesson9_homework_internal_ports_grpc_service_proto_rawDescData
}

var file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes = make([]protoimpl.MessageInfo, 9)
var file_lesson9_homework_internal_ports_grpc_service_proto_goTypes = []interface{}{
	(*CreateAdRequest)(nil),       // 0: ad.CreateAdRequest
	(*ChangeAdStatusRequest)(nil), // 1: ad.ChangeAdStatusRequest
	(*UpdateAdRequest)(nil),       // 2: ad.UpdateAdRequest
	(*AdResponse)(nil),            // 3: ad.AdResponse
	(*User)(nil),                  // 4: ad.User
	(*GetUserRequest)(nil),        // 5: ad.GetUserRequest
	(*GetAdRequest)(nil),          // 6: ad.GetAdRequest
	(*DeleteUserRequest)(nil),     // 7: ad.DeleteUserRequest
	(*DeleteAdRequest)(nil),       // 8: ad.DeleteAdRequest
	(*timestamppb.Timestamp)(nil), // 9: google.protobuf.Timestamp
	(*emptypb.Empty)(nil),         // 10: google.protobuf.Empty
}
var file_lesson9_homework_internal_ports_grpc_service_proto_depIdxs = []int32{
	9,  // 0: ad.AdResponse.creation_date:type_name -> google.protobuf.Timestamp
	9,  // 1: ad.AdResponse.update_date:type_name -> google.protobuf.Timestamp
	0,  // 2: ad.Service.CreateAd:input_type -> ad.CreateAdRequest
	1,  // 3: ad.Service.ChangeAdStatus:input_type -> ad.ChangeAdStatusRequest
	2,  // 4: ad.Service.UpdateAd:input_type -> ad.UpdateAdRequest
	6,  // 5: ad.Service.GetAdByID:input_type -> ad.GetAdRequest
	4,  // 6: ad.Service.CreateUser:input_type -> ad.User
	5,  // 7: ad.Service.GetUser:input_type -> ad.GetUserRequest
	7,  // 8: ad.Service.DeleteUserByID:input_type -> ad.DeleteUserRequest
	8,  // 9: ad.Service.DeleteAdByAuthorID:input_type -> ad.DeleteAdRequest
	3,  // 10: ad.Service.CreateAd:output_type -> ad.AdResponse
	3,  // 11: ad.Service.ChangeAdStatus:output_type -> ad.AdResponse
	3,  // 12: ad.Service.UpdateAd:output_type -> ad.AdResponse
	3,  // 13: ad.Service.GetAdByID:output_type -> ad.AdResponse
	4,  // 14: ad.Service.CreateUser:output_type -> ad.User
	4,  // 15: ad.Service.GetUser:output_type -> ad.User
	10, // 16: ad.Service.DeleteUserByID:output_type -> google.protobuf.Empty
	10, // 17: ad.Service.DeleteAdByAuthorID:output_type -> google.protobuf.Empty
	10, // [10:18] is the sub-list for method output_type
	2,  // [2:10] is the sub-list for method input_type
	2,  // [2:2] is the sub-list for extension type_name
	2,  // [2:2] is the sub-list for extension extendee
	0,  // [0:2] is the sub-list for field type_name
}

func init() { file_lesson9_homework_internal_ports_grpc_service_proto_init() }
func file_lesson9_homework_internal_ports_grpc_service_proto_init() {
	if File_lesson9_homework_internal_ports_grpc_service_proto != nil {
		return
	}
	if !protoimpl.UnsafeEnabled {
		file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[0].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*CreateAdRequest); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[1].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*ChangeAdStatusRequest); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[2].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*UpdateAdRequest); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[3].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*AdResponse); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[4].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*User); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[5].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*GetUserRequest); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[6].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*GetAdRequest); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[7].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*DeleteUserRequest); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes[8].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*DeleteAdRequest); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
	}
	type x struct{}
	out := protoimpl.TypeBuilder{
		File: protoimpl.DescBuilder{
			GoPackagePath: reflect.TypeOf(x{}).PkgPath(),
			RawDescriptor: file_lesson9_homework_internal_ports_grpc_service_proto_rawDesc,
			NumEnums:      0,
			NumMessages:   9,
			NumExtensions: 0,
			NumServices:   1,
		},
		GoTypes:           file_lesson9_homework_internal_ports_grpc_service_proto_goTypes,
		DependencyIndexes: file_lesson9_homework_internal_ports_grpc_service_proto_depIdxs,
		MessageInfos:      file_lesson9_homework_internal_ports_grpc_service_proto_msgTypes,
	}.Build()
	File_lesson9_homework_internal_ports_grpc_service_proto = out.File
	file_lesson9_homework_internal_ports_grpc_service_proto_rawDesc = nil
	file_lesson9_homework_internal_ports_grpc_service_proto_goTypes = nil
	file_lesson9_homework_internal_ports_grpc_service_proto_depIdxs = nil
}
