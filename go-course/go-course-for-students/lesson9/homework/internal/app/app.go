package app

import (
	"errors"
	"homework9/internal/model"
	"time"
)

type App interface {
	CreateAd(title string, text string, authorID int64) (*model.Ad, error)
	ChangeAdStatus(adID int64, authorID int64, published bool) (*model.Ad, error)
	UpdateAd(adId int64, authorID int64, title string, text string) (*model.Ad, error)
	CreateUser(nickname string, email string, userID int64) (*model.User, error)
	UpdateUser(nickname string, email string, userID int64) (*model.User, error)
	GetUserByID(userID int64) (*model.User, error)
	DeleteUserByID(userID int64) error
	GetAdByID(adID int64) (*model.Ad, error)
	DeleteAdByAuthorID(authorID int64) error
	GetPublishedAds() ([]*model.Ad, error)
	GetAdsByAuthorID(authorID int64) ([]*model.Ad, error)
	GetAdsByCreationDate(time time.Time) ([]*model.Ad, error)
	GetAdsByTitle(title string) ([]*model.Ad, error)
}

type AdsRepository interface {
	Put(ad *model.Ad) error
	GetID() int64
	GetAdByID(authorID int64) (*model.Ad, error)
	GetPublishedAds() ([]*model.Ad, error)
	GetAdsByAuthorID(authorID int64) ([]*model.Ad, error)
	GetAdsByCreationDate(date time.Time) ([]*model.Ad, error)
	GetAdsByTitle(title string) ([]*model.Ad, error)
	DeleteAdByAuthorID(authorID int64) error
}

type UserRepository interface {
	Put(user *model.User) error
	GetUserByID(userID int64) (user *model.User, err error)
	DeleteUserByID(userID int64) error
}

type Impl struct {
	adsRepository  AdsRepository
	userRepository UserRepository
}

func NewApp(adsRepo AdsRepository, userRepo UserRepository) App {
	return &Impl{adsRepository: adsRepo, userRepository: userRepo}
}

type PermissionError struct {
	Err error
}

func (p PermissionError) Error() string {
	return p.Err.Error()
}

type ValidationError struct {
	Err error
}

func (v ValidationError) Error() string {
	return v.Err.Error()
}

func (a Impl) CreateUser(nickname string, email string, userID int64) (*model.User, error) {
	var result = &model.User{
		UserID:   userID,
		Nickname: nickname,
		Email:    email,
	}

	var err = a.userRepository.Put(result)
	if err != nil {
		return nil, err
	}

	return result, nil
}

func (a Impl) UpdateUser(nickname string, email string, userID int64) (*model.User, error) {
	var result, err = a.userRepository.GetUserByID(userID)
	if err != nil {
		return nil, err
	}

	result.Nickname = nickname
	result.Email = email

	return result, nil
}

func (a Impl) DeleteUserByID(userID int64) error {
	return a.userRepository.DeleteUserByID(userID)
}

func (a Impl) GetUserByID(userID int64) (*model.User, error) {
	var result, err = a.userRepository.GetUserByID(userID)
	if err != nil {
		return nil, err
	}

	return result, err
}

func (a Impl) CreateAd(title string, text string, authorID int64) (*model.Ad, error) {
	if err, ok := titleAndTextValidate(title, text); !ok {
		return nil, err
	}

	var result = &model.Ad{
		ID:       a.adsRepository.GetID(),
		Title:    title,
		Text:     text,
		AuthorID: authorID,
	}

	result.CreatedDate = time.Now().UTC()

	var err = a.adsRepository.Put(result)
	if err != nil {
		return nil, err
	}

	return result, nil
}

func (a Impl) ChangeAdStatus(adID int64, authorID int64, published bool) (*model.Ad, error) {
	return a.validateAndSetByIDAd(adID, authorID, "", "", published)
}

func (a Impl) UpdateAd(adID int64, authorID int64, title string, text string) (*model.Ad, error) {
	if err, ok := titleAndTextValidate(title, text); !ok {
		return nil, err
	}

	return a.validateAndSetByIDAd(adID, authorID, title, text, false)
}

func (a Impl) GetAdByID(adID int64) (*model.Ad, error) {
	var result, err = a.adsRepository.GetAdByID(adID)
	if err != nil {
		return nil, err
	}

	return result, err
}

func (a Impl) GetPublishedAds() ([]*model.Ad, error) {
	var result, err = a.adsRepository.GetPublishedAds()
	if err != nil {
		return nil, err
	}

	return result, nil
}

func (a Impl) GetAdsByAuthorID(authorID int64) ([]*model.Ad, error) {
	var result, err = a.adsRepository.GetAdsByAuthorID(authorID)
	if err != nil {
		return nil, err
	}

	return result, nil
}

func (a Impl) GetAdsByCreationDate(date time.Time) ([]*model.Ad, error) {
	var result, err = a.adsRepository.GetAdsByCreationDate(date)
	if err != nil {
		return nil, err
	}

	return result, nil
}

func (a Impl) GetAdsByTitle(title string) ([]*model.Ad, error) {
	if err, ok := titleValidate(title); !ok {
		return nil, err
	}

	var result, err = a.adsRepository.GetAdsByTitle(title)
	if err != nil {
		return nil, err
	}

	return result, nil
}

func (a Impl) DeleteAdByAuthorID(authorID int64) error {
	return a.adsRepository.DeleteAdByAuthorID(authorID)
}

func (a Impl) validateAndSetByIDAd(adID int64, authorID int64, title string, text string, published bool) (*model.Ad, error) {
	var result, err = a.GetAdByID(adID)
	if err != nil {
		return nil, err
	}

	if result.AuthorID != authorID {
		return nil, PermissionError{errors.New("not a writer of this ad")}
	}

	if text == "" && title == "" {
		result.Published = published
	} else {
		result.Title = title
		result.Text = text
		result.UpdatedDate = time.Now().UTC()
	}

	return result, nil
}

func titleValidate(title string) (error, bool) {
	if len(title) <= 0 || len(title) >= 100 {
		return ValidationError{errors.New("invalid title")}, false
	}

	return nil, true
}

func titleAndTextValidate(title string, text string) (error, bool) {
	err, ok := titleValidate(title)
	if err != nil {
		return err, false
	}

	if !ok || len(text) <= 0 || len(text) >= 500 {
		return ValidationError{errors.New("invalid text or title")}, false
	}

	return nil, true
}
