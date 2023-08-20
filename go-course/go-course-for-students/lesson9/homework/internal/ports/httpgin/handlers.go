package httpgin

import (
	"errors"
	"github.com/gin-gonic/gin"
	"homework9/internal/app"
	"net/http"
	"strconv"
)

func createAd(a app.App) gin.HandlerFunc {
	return func(c *gin.Context) {
		var reqBody createAdRequest

		if err := c.BindJSON(&reqBody); err != nil {
			c.JSON(http.StatusBadRequest, AdErrorResponse(err))
			return
		}

		ad, err := a.CreateAd(reqBody.Title, reqBody.Text, reqBody.UserID)
		if err != nil {
			c.JSON(errorStatus(err), AdErrorResponse(err))
			return
		}

		c.JSON(http.StatusOK, AdSuccessResponse(ad))
	}
}

func changeAdStatus(a app.App) gin.HandlerFunc {
	return func(c *gin.Context) {
		var reqBody changeAdStatusRequest
		if err := c.BindJSON(&reqBody); err != nil {
			c.JSON(http.StatusBadRequest, AdErrorResponse(err))
			return
		}

		adIDStr := c.Param("ad_id")
		adID, err := strconv.ParseInt(adIDStr, 10, 64)
		if err != nil {
			c.JSON(http.StatusBadRequest, AdErrorResponse(errors.New("not valid key")))
		}

		ad, err := a.ChangeAdStatus(adID, reqBody.UserID, reqBody.Published)
		if err != nil {
			c.JSON(errorStatus(err), AdErrorResponse(err))
			return
		}

		c.JSON(http.StatusOK, AdSuccessResponse(ad))
	}
}

func updateAd(a app.App) gin.HandlerFunc {
	return func(c *gin.Context) {
		var reqBody updateAdRequest
		if err := c.BindJSON(&reqBody); err != nil {
			c.JSON(http.StatusBadRequest, AdErrorResponse(err))
			return
		}

		adIDStr := c.Param("ad_id")

		adID, err := strconv.ParseInt(adIDStr, 10, 64)
		if err != nil {
			c.JSON(http.StatusBadRequest, AdErrorResponse(errors.New("not valid key")))
		}

		ad, err := a.UpdateAd(adID, reqBody.UserID, reqBody.Title, reqBody.Text)
		if err != nil {
			c.JSON(errorStatus(err), AdErrorResponse(err))
			return
		}

		c.JSON(http.StatusOK, AdSuccessResponse(ad))
	}
}

func getListAds(a app.App) gin.HandlerFunc {
	return func(c *gin.Context) {
		ad, err := a.GetPublishedAds()
		if err != nil {
			c.JSON(errorStatus(err), AdErrorResponse(err))
			return
		}

		c.JSON(http.StatusOK, AdsSuccessResponse(ad))
	}
}

func errorStatus(err error) int {
	if errors.As(err, &app.ValidationError{}) {
		return http.StatusBadRequest
	} else if errors.As(err, &app.PermissionError{}) {
		return http.StatusForbidden
	} else {
		return http.StatusInternalServerError
	}
}
