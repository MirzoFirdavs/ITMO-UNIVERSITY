package userrepo

import (
	"errors"
	"homework8/internal/app"
	"sync"

	"homework8/internal/model"
)

type RepoUser struct {
	fromUserIDToUser map[int64]*model.User
	mu               sync.RWMutex
}

func New() app.UserRepository {
	return &RepoUser{
		fromUserIDToUser: make(map[int64]*model.User),
		mu:               sync.RWMutex{},
	}
}

func (repo *RepoUser) Put(user *model.User) error {
	repo.mu.Lock()
	defer repo.mu.Unlock()

	repo.fromUserIDToUser[user.UserID] = user

	return nil
}

func (repo *RepoUser) GetUserByID(userID int64) (ad *model.User, err error) {
	repo.mu.RLock()
	defer repo.mu.RUnlock()

	result, found := repo.fromUserIDToUser[userID]
	if !found {
		return nil, errors.New("user not found")
	}

	return result, nil
}
