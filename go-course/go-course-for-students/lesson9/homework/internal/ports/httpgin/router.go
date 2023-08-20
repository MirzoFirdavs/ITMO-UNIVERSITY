package httpgin

import (
	"github.com/gin-gonic/gin"
	"homework9/internal/app"
)

func AppRouter(r gin.RouterGroup, a app.App) {
	r.POST("/ads", createAd(a))
	r.PUT("/ads/:ad_id/status", changeAdStatus(a))
	r.PUT("/ads/:ad_id", updateAd(a))
	r.GET("/ads", getListAds(a))
}
