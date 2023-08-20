package httpgin

import (
	"github.com/gin-gonic/gin"
	"homework10/internal/model"
	"time"
)

type createAdRequest struct {
	Title  string `json:"title"`
	Text   string `json:"text"`
	UserID int64  `json:"user_id"`
}

type createUserRequest struct {
	Nickname string `json:"nickname"`
	Email    string `json:"email"`
	UserID   int64  `json:"user_id"`
}

type updateUserRequest struct {
	Nickname string `json:"nickname"`
	Email    string `json:"email"`
	UserID   int64  `json:"user_id"`
}

type adResponse struct {
	ID          int64     `json:"id"`
	Title       string    `json:"title"`
	Text        string    `json:"text"`
	AuthorID    int64     `json:"author_id"`
	Published   bool      `json:"published"`
	CreatedDate time.Time `json:"created_date" binding:"required" time_format:"02-01-2006"`
	UpdatedDate time.Time `json:"updated_date" binding:"required" time_format:"02-01-2006"`
}

type userResponse struct {
	Nickname string `json:"nickname"`
	Email    string `json:"email"`
	UserID   int64  `json:"user_id"`
}

type changeAdStatusRequest struct {
	Published bool  `json:"published"`
	UserID    int64 `json:"user_id"`
}

type updateAdRequest struct {
	Title  string `json:"title"`
	Text   string `json:"text"`
	UserID int64  `json:"user_id"`
}

func AdSuccessResponse(ad *model.Ad) *gin.H {
	return &gin.H{
		"data":  newAdResponse(ad),
		"error": nil,
	}
}

func UserSuccessResponse(user *model.User) *gin.H {
	return &gin.H{
		"data":  newUserResponse(user),
		"error": nil,
	}
}

func newUserResponse(user *model.User) userResponse {
	return userResponse{
		Nickname: user.Nickname,
		Email:    user.Email,
		UserID:   user.UserID,
	}
}

func AdsSuccessResponse(ads []*model.Ad) *gin.H {
	adsResponse := make([]adResponse, 0)
	for _, ad := range ads {
		adsResponse = append(adsResponse, newAdResponse(ad))
	}
	return &gin.H{
		"data":  adsResponse,
		"error": nil,
	}
}

func ErrorResponse(err error) *gin.H {
	return &gin.H{
		"data":  nil,
		"error": err.Error(),
	}
}

func newAdResponse(ad *model.Ad) adResponse {
	return adResponse{
		ID:          ad.ID,
		Title:       ad.Title,
		Text:        ad.Text,
		AuthorID:    ad.AuthorID,
		Published:   ad.Published,
		CreatedDate: ad.CreatedDate,
		UpdatedDate: ad.UpdatedDate,
	}
}
