package tests

import (
	"github.com/stretchr/testify/require"
	"homework10/internal/adapters/adrepo"
	"homework10/internal/adapters/userrepo"
	"homework10/internal/model"
	"strings"
	"testing"
)

func FuzzUserRepositoryPut(f *testing.F) {
	users := make([]model.User, 0, 1000)

	for i := 1; i <= 1000; i++ {
		users = append(users, model.User{
			UserID:   int64(i),
			Nickname: strings.Repeat("a", i),
			Email:    strings.Repeat("f", i),
		})
	}

	for _, entity := range users {
		f.Add(entity.Nickname, entity.Email)
	}

	repo := userrepo.New()
	f.Fuzz(func(t *testing.T, nickname string, email string) {
		err := repo.Put(&model.User{Nickname: nickname, Email: email})
		require.NoError(t, err)
	})
}

func FuzzAdRepositoryPut(f *testing.F) {
	ads := make([]model.Ad, 0, 1000)

	for i := 1; i <= 1000; i++ {
		ads = append(ads, model.Ad{
			ID:    int64(i),
			Title: strings.Repeat("a", i),
			Text:  strings.Repeat("f", i),
		})
	}

	for _, entity := range ads {
		f.Add(entity.Title, entity.Text)
	}

	repo := adrepo.New()
	f.Fuzz(func(t *testing.T, nickname string, email string) {
		err := repo.Put(&model.Ad{Title: nickname, Text: email})
		require.NoError(t, err)
	})
}
