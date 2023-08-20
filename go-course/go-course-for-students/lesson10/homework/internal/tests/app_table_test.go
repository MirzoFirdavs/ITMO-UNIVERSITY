package tests

import (
	"github.com/stretchr/testify/assert"
	"homework10/internal/model"
	"testing"
	"time"
)

func TestGetAdsByAuthorID(t *testing.T) {
	t.Run("table tests", func(t *testing.T) {
		type Test struct {
			In     int64
			Expect []model.Ad
		}

		client := getTestClient()

		resp1, err := client.createAd(15, "title1", "text1")
		assert.NoError(t, err)
		resp2, err := client.createAd(15, "title2", "text2")
		assert.NoError(t, err)
		resp3, err := client.createAd(15, "title3", "text3")
		assert.NoError(t, err)
		resp4, err := client.createAd(15, "title4", "text4")
		assert.NoError(t, err)
		resp5, err := client.createAd(20, "title5", "text5")
		assert.NoError(t, err)
		resp6, err := client.createAd(20, "title6", "text6")
		assert.NoError(t, err)
		resp7, err := client.createAd(20, "title7", "text7")
		assert.NoError(t, err)
		resp8, err := client.createAd(20, "title8", "text8")
		assert.NoError(t, err)
		resp9, err := client.createAd(16, "title9", "text9")
		assert.NoError(t, err)
		resp10, err := client.createAd(16, "title10", "text10")
		assert.NoError(t, err)
		resp11, err := client.createAd(16, "title11", "text11")
		assert.NoError(t, err)
		resp12, err := client.createAd(16, "title12", "text12")
		assert.NoError(t, err)
		resp13, err := client.createAd(10, "title13", "text13")
		assert.NoError(t, err)
		resp14, err := client.createAd(10, "title14", "text14")
		assert.NoError(t, err)
		resp15, err := client.createAd(10, "title15", "text15")
		assert.NoError(t, err)
		resp16, err := client.createAd(10, "title16", "text16")
		assert.NoError(t, err)
		resp17, err := client.createAd(5, "title17", "text17")
		assert.NoError(t, err)
		resp18, err := client.createAd(5, "title18", "text18")
		assert.NoError(t, err)
		resp19, err := client.createAd(5, "title19", "text19")
		assert.NoError(t, err)
		resp20, err := client.createAd(5, "title20", "text20")
		assert.NoError(t, err)

		resp1, err = client.changeAdStatus(resp1.Data.AuthorID, resp1.Data.ID, true)
		assert.NoError(t, err)
		resp2, err = client.changeAdStatus(resp2.Data.AuthorID, resp2.Data.ID, true)
		assert.NoError(t, err)
		resp3, err = client.changeAdStatus(resp3.Data.AuthorID, resp3.Data.ID, true)
		assert.NoError(t, err)
		resp4, err = client.changeAdStatus(resp4.Data.AuthorID, resp4.Data.ID, true)
		assert.NoError(t, err)
		resp5, err = client.changeAdStatus(resp5.Data.AuthorID, resp5.Data.ID, true)
		assert.NoError(t, err)
		resp6, err = client.changeAdStatus(resp6.Data.AuthorID, resp6.Data.ID, true)
		assert.NoError(t, err)
		resp7, err = client.changeAdStatus(resp7.Data.AuthorID, resp7.Data.ID, true)
		assert.NoError(t, err)
		resp8, err = client.changeAdStatus(resp8.Data.AuthorID, resp8.Data.ID, true)
		assert.NoError(t, err)
		resp9, err = client.changeAdStatus(resp9.Data.AuthorID, resp9.Data.ID, true)
		assert.NoError(t, err)
		resp10, err = client.changeAdStatus(resp10.Data.AuthorID, resp10.Data.ID, true)
		assert.NoError(t, err)
		resp11, err = client.changeAdStatus(resp11.Data.AuthorID, resp11.Data.ID, true)
		assert.NoError(t, err)
		resp12, err = client.changeAdStatus(resp12.Data.AuthorID, resp12.Data.ID, true)
		assert.NoError(t, err)
		resp13, err = client.changeAdStatus(resp13.Data.AuthorID, resp13.Data.ID, true)
		assert.NoError(t, err)
		resp14, err = client.changeAdStatus(resp14.Data.AuthorID, resp14.Data.ID, true)
		assert.NoError(t, err)
		resp15, err = client.changeAdStatus(resp15.Data.AuthorID, resp15.Data.ID, true)
		assert.NoError(t, err)
		resp16, err = client.changeAdStatus(resp16.Data.AuthorID, resp16.Data.ID, true)
		assert.NoError(t, err)
		resp17, err = client.changeAdStatus(resp17.Data.AuthorID, resp17.Data.ID, true)
		assert.NoError(t, err)
		resp18, err = client.changeAdStatus(resp18.Data.AuthorID, resp18.Data.ID, true)
		assert.NoError(t, err)
		resp19, err = client.changeAdStatus(resp19.Data.AuthorID, resp19.Data.ID, true)
		assert.NoError(t, err)
		resp20, err = client.changeAdStatus(resp20.Data.AuthorID, resp20.Data.ID, true)
		assert.NoError(t, err)

		tests := [...]Test{
			{15, []model.Ad{
				{
					ID:          resp1.Data.ID,
					Title:       resp1.Data.Title,
					Text:        resp1.Data.Text,
					AuthorID:    resp1.Data.AuthorID,
					Published:   true,
					CreatedDate: time.Now().UTC(),
					UpdatedDate: time.Now().UTC(),
				},
				{
					ID:          resp2.Data.ID,
					Title:       resp2.Data.Title,
					Text:        resp2.Data.Text,
					AuthorID:    resp2.Data.AuthorID,
					Published:   true,
					CreatedDate: time.Now().UTC(),
					UpdatedDate: time.Now().UTC(),
				},
				{
					ID:          resp3.Data.ID,
					Title:       resp3.Data.Title,
					Text:        resp3.Data.Text,
					AuthorID:    resp3.Data.AuthorID,
					Published:   true,
					CreatedDate: time.Now().UTC(),
					UpdatedDate: time.Now().UTC(),
				},
				{
					ID:          resp4.Data.ID,
					Title:       resp4.Data.Title,
					Text:        resp4.Data.Text,
					AuthorID:    resp4.Data.AuthorID,
					Published:   true,
					CreatedDate: time.Now().UTC(),
					UpdatedDate: time.Now().UTC(),
				},
			},
			},
			{20, []model.Ad{
				{
					ID:          resp5.Data.ID,
					Title:       resp5.Data.Title,
					Text:        resp5.Data.Text,
					AuthorID:    resp5.Data.AuthorID,
					Published:   true,
					CreatedDate: time.Now().UTC(),
					UpdatedDate: time.Now().UTC(),
				},
				{
					ID:          resp6.Data.ID,
					Title:       resp6.Data.Title,
					Text:        resp6.Data.Text,
					AuthorID:    resp6.Data.AuthorID,
					Published:   true,
					CreatedDate: time.Now().UTC(),
					UpdatedDate: time.Now().UTC(),
				},
				{
					ID:          resp7.Data.ID,
					Title:       resp7.Data.Title,
					Text:        resp7.Data.Text,
					AuthorID:    resp7.Data.AuthorID,
					Published:   true,
					CreatedDate: time.Now().UTC(),
					UpdatedDate: time.Now().UTC(),
				},
				{
					ID:          resp8.Data.ID,
					Title:       resp8.Data.Title,
					Text:        resp8.Data.Text,
					AuthorID:    resp8.Data.AuthorID,
					Published:   true,
					CreatedDate: time.Now().UTC(),
					UpdatedDate: time.Now().UTC(),
				},
			},
			},
			{16, []model.Ad{
				{
					ID:          resp9.Data.ID,
					Title:       resp9.Data.Title,
					Text:        resp9.Data.Text,
					AuthorID:    resp9.Data.AuthorID,
					Published:   true,
					CreatedDate: time.Now().UTC(),
					UpdatedDate: time.Now().UTC(),
				},
				{
					ID:          resp10.Data.ID,
					Title:       resp10.Data.Title,
					Text:        resp10.Data.Text,
					AuthorID:    resp10.Data.AuthorID,
					Published:   true,
					CreatedDate: time.Now().UTC(),
					UpdatedDate: time.Now().UTC(),
				},
				{
					ID:          resp11.Data.ID,
					Title:       resp11.Data.Title,
					Text:        resp11.Data.Text,
					AuthorID:    resp11.Data.AuthorID,
					Published:   true,
					CreatedDate: time.Now().UTC(),
					UpdatedDate: time.Now().UTC(),
				},
				{
					ID:          resp12.Data.ID,
					Title:       resp12.Data.Title,
					Text:        resp12.Data.Text,
					AuthorID:    resp12.Data.AuthorID,
					Published:   true,
					CreatedDate: time.Now().UTC(),
					UpdatedDate: time.Now().UTC(),
				},
			},
			},
			{10, []model.Ad{
				{
					ID:          resp13.Data.ID,
					Title:       resp13.Data.Title,
					Text:        resp13.Data.Text,
					AuthorID:    resp13.Data.AuthorID,
					Published:   true,
					CreatedDate: time.Now().UTC(),
					UpdatedDate: time.Now().UTC(),
				},
				{
					ID:          resp14.Data.ID,
					Title:       resp14.Data.Title,
					Text:        resp14.Data.Text,
					AuthorID:    resp14.Data.AuthorID,
					Published:   true,
					CreatedDate: time.Now().UTC(),
					UpdatedDate: time.Now().UTC(),
				},
				{
					ID:          resp15.Data.ID,
					Title:       resp15.Data.Title,
					Text:        resp15.Data.Text,
					AuthorID:    resp15.Data.AuthorID,
					Published:   true,
					CreatedDate: time.Now().UTC(),
					UpdatedDate: time.Now().UTC(),
				},
				{
					ID:          resp16.Data.ID,
					Title:       resp16.Data.Title,
					Text:        resp16.Data.Text,
					AuthorID:    resp16.Data.AuthorID,
					Published:   true,
					CreatedDate: time.Now().UTC(),
					UpdatedDate: time.Now().UTC(),
				},
			},
			},
			{5, []model.Ad{
				{
					ID:          resp17.Data.ID,
					Title:       resp17.Data.Title,
					Text:        resp17.Data.Text,
					AuthorID:    resp17.Data.AuthorID,
					Published:   true,
					CreatedDate: time.Now().UTC(),
					UpdatedDate: time.Now().UTC(),
				},
				{
					ID:          resp18.Data.ID,
					Title:       resp18.Data.Title,
					Text:        resp18.Data.Text,
					AuthorID:    resp18.Data.AuthorID,
					Published:   true,
					CreatedDate: time.Now().UTC(),
					UpdatedDate: time.Now().UTC(),
				},
				{
					ID:          resp19.Data.ID,
					Title:       resp19.Data.Title,
					Text:        resp19.Data.Text,
					AuthorID:    resp1.Data.AuthorID,
					Published:   true,
					CreatedDate: time.Now().UTC(),
					UpdatedDate: time.Now().UTC(),
				},
				{
					ID:          resp20.Data.ID,
					Title:       resp20.Data.Title,
					Text:        resp20.Data.Text,
					AuthorID:    resp20.Data.AuthorID,
					Published:   true,
					CreatedDate: time.Now().UTC(),
					UpdatedDate: time.Now().UTC(),
				},
			},
			},
		}

		for idx, test := range tests {
			got, err := client.getAdsByAuthorID(test.In)
			assert.NoError(t, err)

			if !equal(test.Expect, got.Data) {
				t.Fatalf(`test%d: expect %v got %v`, idx, test.Expect, got)
			}
		}
	})
}

func equal(ads []model.Ad, got []adData) bool {
	var cnt = 0

	for i := 0; i < len(ads); i++ {
		if ads[i].ID == got[i].ID && ads[i].Title == got[i].Title && ads[i].Text == got[i].Text {
			cnt++
		}
	}

	return cnt == len(ads)
}
