package adrepo

import (
	"errors"
	"homework8/internal/app"
	"homework8/internal/model"
	"strings"
	"sync"
	"sync/atomic"
	"time"
)

type RepoAds struct {
	adID                 int64
	fromAdIDToAd         map[int64]*model.Ad
	fromAuthorIDToAd     map[int64][]*model.Ad
	fromCreationDateToAd map[time.Time][]*model.Ad
	allAds               []*model.Ad

	adIDMu         sync.RWMutex
	authorMu       sync.RWMutex
	creationDateMu sync.RWMutex
	allAdsMu       sync.RWMutex
}

func New() app.AdsRepository {
	return &RepoAds{
		adID:                 0,
		fromAdIDToAd:         make(map[int64]*model.Ad),
		fromAuthorIDToAd:     make(map[int64][]*model.Ad),
		fromCreationDateToAd: make(map[time.Time][]*model.Ad),
		allAds:               make([]*model.Ad, 0),

		adIDMu:         sync.RWMutex{},
		authorMu:       sync.RWMutex{},
		creationDateMu: sync.RWMutex{},
		allAdsMu:       sync.RWMutex{},
	}
}

func (repo *RepoAds) Put(ad *model.Ad) error {
	repo.adIDMu.Lock()
	repo.authorMu.Lock()
	repo.creationDateMu.Lock()
	repo.allAdsMu.Lock()

	defer repo.adIDMu.Unlock()
	defer repo.authorMu.Unlock()
	defer repo.creationDateMu.Unlock()
	defer repo.allAdsMu.Unlock()

	repo.fromAdIDToAd[ad.ID] = ad
	repo.fromAuthorIDToAd[ad.AuthorID] = append(repo.fromAuthorIDToAd[ad.AuthorID], ad)
	repo.fromCreationDateToAd[ad.CreatedDate] = append(repo.fromCreationDateToAd[ad.CreatedDate], ad)
	repo.allAds = append(repo.allAds, ad)

	atomic.AddInt64(&repo.adID, 1)

	return nil
}

func (repo *RepoAds) GetID() int64 {
	return repo.adID
}

func (repo *RepoAds) GetAdByID(adID int64) (ad *model.Ad, err error) {
	repo.adIDMu.RLock()
	defer repo.adIDMu.RUnlock()

	result, found := repo.fromAdIDToAd[adID]
	if !found {
		return nil, errors.New("ad not found")
	}

	return result, nil
}

func (repo *RepoAds) GetAdsByAuthorID(authorID int64) ([]*model.Ad, error) {
	repo.authorMu.RLock()
	defer repo.authorMu.RUnlock()

	result, found := repo.fromAuthorIDToAd[authorID]
	if !found {
		return nil, errors.New("ads not found")
	}

	return result, nil
}

func (repo *RepoAds) GetAdsByCreationDate(date time.Time) ([]*model.Ad, error) {
	repo.creationDateMu.RLock()
	defer repo.creationDateMu.RUnlock()

	result, found := repo.fromCreationDateToAd[date]
	if !found {
		return nil, errors.New("ads not found")
	}

	return result, nil
}

func (repo *RepoAds) GetPublishedAds() ([]*model.Ad, error) {
	return repo.getAdsByPredicate("", true)
}

func (repo *RepoAds) GetAdsByTitle(title string) ([]*model.Ad, error) {
	return repo.getAdsByPredicate(title, false)
}

func (repo *RepoAds) getAdsByPredicate(title string, published bool) ([]*model.Ad, error) {
	repo.allAdsMu.RLock()
	defer repo.allAdsMu.RUnlock()

	var result []*model.Ad
	for _, ad := range repo.allAds {
		if !published && ad.Published && strings.Contains(ad.Title, title) {
			result = append(result, ad)
		}
		if published && ad.Published {
			result = append(result, ad)
		}
	}

	if len(result) == 0 {
		return nil, errors.New("ads not found")
	}

	return result, nil
}
