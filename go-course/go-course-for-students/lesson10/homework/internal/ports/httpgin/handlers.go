package httpgin

import (
	"errors"
	"github.com/gin-gonic/gin"
	"homework10/internal/app"
	"net/http"
	"strconv"
)

func createAd(a app.App) gin.HandlerFunc {
	return func(c *gin.Context) {
		var reqBody createAdRequest

		if err := c.BindJSON(&reqBody); err != nil {
			c.JSON(http.StatusBadRequest, ErrorResponse(err))
			return
		}

		ad, err := a.CreateAd(reqBody.Title, reqBody.Text, reqBody.UserID)
		if err != nil {
			c.JSON(errorStatus(err), ErrorResponse(err))
			return
		}

		c.JSON(http.StatusOK, AdSuccessResponse(ad))
	}
}

func changeAdStatus(a app.App) gin.HandlerFunc {
	return func(c *gin.Context) {
		var reqBody changeAdStatusRequest
		if err := c.BindJSON(&reqBody); err != nil {
			c.JSON(http.StatusBadRequest, ErrorResponse(err))
			return
		}

		adIDStr := c.Param("ad_id")
		adID, err := strconv.ParseInt(adIDStr, 10, 64)
		if err != nil {
			c.JSON(http.StatusBadRequest, ErrorResponse(errors.New("not valid key")))
		}

		ad, err := a.ChangeAdStatus(adID, reqBody.UserID, reqBody.Published)
		if err != nil {
			c.JSON(errorStatus(err), ErrorResponse(err))
			return
		}

		c.JSON(http.StatusOK, AdSuccessResponse(ad))
	}
}

func updateAd(a app.App) gin.HandlerFunc {
	return func(c *gin.Context) {
		var reqBody updateAdRequest
		if err := c.BindJSON(&reqBody); err != nil {
			c.JSON(http.StatusBadRequest, ErrorResponse(err))
			return
		}

		adIDStr := c.Param("ad_id")

		adID, err := strconv.ParseInt(adIDStr, 10, 64)
		if err != nil {
			c.JSON(http.StatusBadRequest, ErrorResponse(errors.New("not valid key")))
		}

		ad, err := a.UpdateAd(adID, reqBody.UserID, reqBody.Title, reqBody.Text)
		if err != nil {
			c.JSON(errorStatus(err), ErrorResponse(err))
			return
		}

		c.JSON(http.StatusOK, AdSuccessResponse(ad))
	}
}

func getAdByID(a app.App) gin.HandlerFunc {
	return func(c *gin.Context) {
		adIDStr := c.Param("ad_id")

		adID, err := strconv.ParseInt(adIDStr, 10, 64)
		if err != nil {
			c.JSON(http.StatusBadRequest, ErrorResponse(errors.New("not valid key")))
		}

		ad, err := a.GetAdByID(adID)
		if err != nil {
			c.JSON(errorStatus(err), ErrorResponse(err))
			return
		}

		c.JSON(http.StatusOK, AdSuccessResponse(ad))
	}
}

func getAdsByTitle(a app.App) gin.HandlerFunc {
	return func(c *gin.Context) {
		title := c.Param("title")
		ad, err := a.GetAdsByTitle(title)
		if err != nil {
			c.JSON(errorStatus(err), ErrorResponse(err))
			return
		}

		c.JSON(http.StatusOK, AdsSuccessResponse(ad))
	}
}

func getAdsByAuthorID(a app.App) gin.HandlerFunc {
	return func(c *gin.Context) {
		authorIDStr := c.Param("ad_id")

		authorID, err := strconv.ParseInt(authorIDStr, 10, 64)
		if err != nil {
			c.JSON(http.StatusBadRequest, ErrorResponse(errors.New("not valid key")))
		}

		ad, err := a.GetAdsByAuthorID(authorID)
		if err != nil {
			c.JSON(errorStatus(err), ErrorResponse(err))
			return
		}

		c.JSON(http.StatusOK, AdsSuccessResponse(ad))
	}
}

func getListAds(a app.App) gin.HandlerFunc {
	return func(c *gin.Context) {
		ad, err := a.GetPublishedAds()
		if err != nil {
			c.JSON(errorStatus(err), ErrorResponse(err))
			return
		}

		c.JSON(http.StatusOK, AdsSuccessResponse(ad))
	}
}

func createUser(a app.App) gin.HandlerFunc {
	return func(c *gin.Context) {
		var reqBody createUserRequest

		if err := c.BindJSON(&reqBody); err != nil {
			c.JSON(http.StatusBadRequest, ErrorResponse(err))
			return
		}

		user, err := a.CreateUser(reqBody.Nickname, reqBody.Email, reqBody.UserID)
		if err != nil {
			c.JSON(errorStatus(err), ErrorResponse(err))
			return
		}

		c.JSON(http.StatusOK, UserSuccessResponse(user))
	}
}

func updateUser(a app.App) gin.HandlerFunc {
	return func(c *gin.Context) {
		var reqBody updateUserRequest
		if err := c.BindJSON(&reqBody); err != nil {
			c.JSON(http.StatusBadRequest, ErrorResponse(err))
			return
		}

		userIDStr := c.Param("user_id")

		userID, err := strconv.ParseInt(userIDStr, 10, 64)
		if err != nil {
			c.JSON(http.StatusBadRequest, ErrorResponse(errors.New("not valid key")))
		}

		user, err := a.UpdateUser(reqBody.Nickname, reqBody.Email, userID)
		if err != nil {
			c.JSON(errorStatus(err), ErrorResponse(err))
			return
		}

		c.JSON(http.StatusOK, UserSuccessResponse(user))
	}
}

func getUserByID(a app.App) gin.HandlerFunc {
	return func(c *gin.Context) {
		userIDStr := c.Param("user_id")

		userID, err := strconv.ParseInt(userIDStr, 10, 64)
		if err != nil {
			c.JSON(http.StatusBadRequest, ErrorResponse(errors.New("not valid key")))
		}

		user, err := a.GetUserByID(userID)
		if err != nil {
			c.JSON(errorStatus(err), ErrorResponse(err))
			return
		}

		c.JSON(http.StatusOK, UserSuccessResponse(user))
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
