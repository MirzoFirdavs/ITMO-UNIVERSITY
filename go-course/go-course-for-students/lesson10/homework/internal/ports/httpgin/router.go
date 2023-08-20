package httpgin

import (
	"github.com/gin-gonic/gin"
	"homework10/internal/app"
)

func AppRouter(r gin.RouterGroup, a app.App) {
	r.POST("/ads", createAd(a))
	r.PUT("/ads/:ad_id/status", changeAdStatus(a))
	r.PUT("/ads/:ad_id", updateAd(a))
	r.GET("/ads", getListAds(a))
	r.GET("ads/:ad_id", getAdByID(a))
	r.GET("ads/title/:title", getAdsByTitle(a))
	r.GET("ads/authorID/:ad_id", getAdsByAuthorID(a))
	r.POST("/user", createUser(a))
	r.PUT("/user/:user_id", updateUser(a))
	r.GET("/user/:user_id", getUserByID(a))
}
