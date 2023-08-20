package tests

import (
	"homework10/internal/adapters/userrepo"
	"homework10/internal/model"
	"strings"
	"testing"
)

func BenchmarkMapRepositorySave(b *testing.B) {
	u := userrepo.New()

	users := make([]model.User, 0, 1000)

	for i := 1; i <= 1000; i++ {
		users = append(users, model.User{
			UserID:   int64(i),
			Nickname: strings.Repeat("a", i),
			Email:    strings.Repeat("f", i),
		})
	}

	for i := 0; i < b.N; i++ {
		_ = u.Put(&users[i%1000])
	}
}
