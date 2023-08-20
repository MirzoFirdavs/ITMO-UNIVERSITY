package app

import (
	"errors"
	"github.com/valyala/fasthttp"
	"homework6/internal/ads"
	"net/http"
)

type App interface {
	CreateAd(ctx *fasthttp.RequestCtx, title string, text string, authorID int64) (*ads.Ad, error)
	ChangeAdStatus(ctx *fasthttp.RequestCtx, adID int64, userID int64, published bool) (*ads.Ad, error)
	UpdateAd(ctx *fasthttp.RequestCtx, adId int64, userID int64, title string, text string) (*ads.Ad, error)
}

type Repository interface {
	Put(ad *ads.Ad) error
	GetID() int64
	GetAdByID(authorID int64) (*ads.Ad, error)
}

type Ads struct {
	repository Repository
}

func NewApp(repo Repository) App {
	return &Ads{repository: repo}
}

func (a Ads) CreateAd(ctx *fasthttp.RequestCtx, title string, text string, authorID int64) (*ads.Ad, error) {
	if err, ok := a.titleAndTextValidate(ctx, title, text); !ok {
		return nil, err
	}

	var result = &ads.Ad{
		ID:       a.repository.GetID(),
		Title:    title,
		Text:     text,
		AuthorID: authorID,
	}

	var err = a.repository.Put(result)
	if err != nil {
		ctx.Error(err.Error(), http.StatusInternalServerError)
		return nil, err
	}

	return result, nil
}

func (a Ads) ChangeAdStatus(ctx *fasthttp.RequestCtx, adID int64, authorID int64, published bool) (*ads.Ad, error) {
	return a.authorValidateAndGetByIDAd(ctx, adID, authorID, "", "", published)
}

func (a Ads) UpdateAd(ctx *fasthttp.RequestCtx, adID int64, authorID int64, title string, text string) (*ads.Ad, error) {
	if err, ok := a.titleAndTextValidate(ctx, title, text); !ok {
		return nil, err
	}

	return a.authorValidateAndGetByIDAd(ctx, adID, authorID, title, text, false)
}

func (a Ads) authorValidateAndGetByIDAd(ctx *fasthttp.RequestCtx, adID int64, authorID int64, title string, text string, published bool) (*ads.Ad, error) {
	var result, err = a.repository.GetAdByID(adID)
	if err != nil {
		ctx.Error(err.Error(), http.StatusInternalServerError)
		return nil, err
	}

	if result.AuthorID != authorID {
		ctx.Error("not a writer of this ad", 403)
		return nil, errors.New("not a writer of this ad")
	}

	if text == "" && title == "" {
		result.Published = published
	} else {
		result.Title = title
		result.Text = text
	}

	return result, nil
}

func (a Ads) titleAndTextValidate(ctx *fasthttp.RequestCtx, title string, text string) (error, bool) {
	if len(title) <= 0 || len(title) >= 100 || len(text) <= 0 || len(text) >= 500 {
		ctx.Error("invalid text or title", 400)
		return errors.New("invalid text or title"), false
	}

	return nil, true
}
