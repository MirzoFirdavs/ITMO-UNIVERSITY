package adrepo

import (
	"errors"
	"homework6/internal/ads"
	"homework6/internal/app"
)

type RepoAds struct {
	uniqueID     int64
	fromAdIDToAd map[int64]*ads.Ad
}

func New() app.Repository {
	return &RepoAds{
		uniqueID:     0,
		fromAdIDToAd: make(map[int64]*ads.Ad),
	}
}

func (repo *RepoAds) Put(ad *ads.Ad) error {
	repo.fromAdIDToAd[ad.ID] = ad
	repo.uniqueID++
	return nil
}

func (repo *RepoAds) GetID() int64 {
	return repo.uniqueID
}

func (repo *RepoAds) GetAdByID(authorID int64) (ad *ads.Ad, err error) {
	result, found := repo.fromAdIDToAd[authorID]
	if !found {
		return nil, errors.New("ads not found")
	}

	return result, nil
}
