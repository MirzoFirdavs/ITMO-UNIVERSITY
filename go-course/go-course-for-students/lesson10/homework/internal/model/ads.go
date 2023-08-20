package model

import "time"

type Ad struct {
	ID          int64
	Title       string
	Text        string
	AuthorID    int64
	Published   bool
	CreatedDate time.Time
	UpdatedDate time.Time
}
