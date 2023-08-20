package userrepo

import (
	"errors"
	"homework10/internal/app"
	"homework10/internal/model"
	"sync"
)

var ErrUserNot = errors.New("user not found")

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
		return nil, ErrUserNot
	}

	return result, nil
}

func (repo *RepoUser) DeleteUserByID(userID int64) error {
	var _, err = repo.GetUserByID(userID)
	if err != nil {
		return err
	} else {
		delete(repo.fromUserIDToUser, userID)
		return nil
	}
}
