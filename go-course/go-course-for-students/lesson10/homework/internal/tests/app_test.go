package tests

import (
	"github.com/stretchr/testify/assert"
	"strings"
	"testing"
)

func TestCreateAd(t *testing.T) {
	client := getTestClient()

	response, err := client.createAd(123, "hello", "world")
	assert.NoError(t, err)
	assert.Zero(t, response.Data.ID)
	assert.Equal(t, response.Data.Title, "hello")
	assert.Equal(t, response.Data.Text, "world")
	assert.Equal(t, response.Data.AuthorID, int64(123))
	assert.False(t, response.Data.Published)
}

func TestChangeAdStatus(t *testing.T) {
	client := getTestClient()

	response, err := client.createAd(123, "hello", "world")
	assert.NoError(t, err)

	response, err = client.changeAdStatus(123, response.Data.ID, true)
	assert.NoError(t, err)
	assert.True(t, response.Data.Published)

	response, err = client.changeAdStatus(123, response.Data.ID, false)
	assert.NoError(t, err)
	assert.False(t, response.Data.Published)

	response, err = client.changeAdStatus(123, response.Data.ID, false)
	assert.NoError(t, err)
	assert.False(t, response.Data.Published)
}

func TestUpdateAd(t *testing.T) {
	client := getTestClient()

	response, err := client.createAd(123, "hello", "world")
	assert.NoError(t, err)

	response, err = client.updateAd(123, response.Data.ID, "привет", "мир")
	assert.NoError(t, err)
	assert.Equal(t, response.Data.Title, "привет")
	assert.Equal(t, response.Data.Text, "мир")
}

func TestListAds(t *testing.T) {
	client := getTestClient()

	response, err := client.createAd(123, "hello", "world")
	assert.NoError(t, err)

	publishedAd, err := client.changeAdStatus(123, response.Data.ID, true)
	assert.NoError(t, err)

	_, err = client.createAd(123, "best cat", "not for sale")
	assert.NoError(t, err)

	ads, err := client.listAds()
	assert.NoError(t, err)
	assert.Len(t, ads.Data, 1)
	assert.Equal(t, ads.Data[0].ID, publishedAd.Data.ID)
	assert.Equal(t, ads.Data[0].Title, publishedAd.Data.Title)
	assert.Equal(t, ads.Data[0].Text, publishedAd.Data.Text)
	assert.Equal(t, ads.Data[0].AuthorID, publishedAd.Data.AuthorID)
	assert.True(t, ads.Data[0].Published)
}

func TestCreateUser(t *testing.T) {
	client := getTestClient()

	response, err := client.createUser(123, "Nick", "email@com.com")
	assert.NoError(t, err)
	assert.Equal(t, response.Data.Nickname, "Nick")
	assert.Equal(t, response.Data.Email, "email@com.com")
	assert.Equal(t, response.Data.UserID, int64(123))
}

func TestUpdateUser(t *testing.T) {
	client := getTestClient()

	_, err := client.createUser(123, "Nick", "example@mail.com")
	assert.NoError(t, err)

	resp, err := client.updateUser(123, "привет", "мир")
	assert.NoError(t, err)
	assert.Equal(t, resp.Data.Nickname, "привет")
	assert.Equal(t, resp.Data.Email, "мир")
}

func TestGetUserByID(t *testing.T) {
	client := getTestClient()

	_, err := client.createUser(123, "hello", "world")
	assert.NoError(t, err)

	_, err = client.createUser(125, "bestCat", "notForSale")
	assert.NoError(t, err)

	user1, err := client.getUserByID(125)
	assert.NoError(t, err)
	assert.Equal(t, user1.Data.Nickname, "bestCat")
	assert.Equal(t, user1.Data.Email, "notForSale")

	user2, err := client.getUserByID(123)
	assert.NoError(t, err)
	assert.Equal(t, user2.Data.Nickname, "hello")
	assert.Equal(t, user2.Data.Email, "world")
}

func TestGetAdByID(t *testing.T) {
	client := getTestClient()

	request1, err := client.createAd(123, "hello", "world")
	assert.NoError(t, err)

	request2, err := client.createAd(125, "bestCat", "notForSale")
	assert.NoError(t, err)

	ad1, err := client.getAdByID(0)
	assert.NoError(t, err)
	assert.Equal(t, ad1.Data.Title, request1.Data.Title)
	assert.Equal(t, ad1.Data.Text, request1.Data.Text)
	assert.Equal(t, ad1.Data.AuthorID, request1.Data.AuthorID)

	ad2, err := client.getAdByID(1)
	assert.NoError(t, err)
	assert.Equal(t, ad2.Data.Title, request2.Data.Title)
	assert.Equal(t, ad2.Data.Text, request2.Data.Text)
	assert.Equal(t, ad2.Data.AuthorID, request2.Data.AuthorID)
}

func TestGetAdsByTitle(t *testing.T) {
	client := getTestClient()

	response, err := client.createAd(123, "hello", "world")
	assert.NoError(t, err)

	publishedAd, err := client.changeAdStatus(123, response.Data.ID, true)
	assert.NoError(t, err)

	_, err = client.createAd(123, "best cat", "not for sale")
	assert.NoError(t, err)

	ads, err := client.getAdsByTitle("hello")
	assert.NoError(t, err)
	assert.Len(t, ads.Data, 1)
	assert.Equal(t, ads.Data[0].ID, publishedAd.Data.ID)
	assert.Equal(t, ads.Data[0].Title, publishedAd.Data.Title)
	assert.Equal(t, ads.Data[0].Text, publishedAd.Data.Text)
	assert.Equal(t, ads.Data[0].AuthorID, publishedAd.Data.AuthorID)
	assert.True(t, ads.Data[0].Published)
}

func TestChangeStatusAdOfAnotherUser(t *testing.T) {
	client := getTestClient()

	resp, err := client.createAd(123, "hello", "world")
	assert.NoError(t, err)

	_, err = client.changeAdStatus(100, resp.Data.ID, true)
	assert.ErrorIs(t, err, ErrForbidden)
}

func TestUpdateAdOfAnotherUser(t *testing.T) {
	client := getTestClient()

	resp, err := client.createAd(123, "hello", "world")
	assert.NoError(t, err)

	_, err = client.updateAd(100, resp.Data.ID, "title", "text")
	assert.ErrorIs(t, err, ErrForbidden)
}

func TestCreateAd_ID(t *testing.T) {
	client := getTestClient()

	resp, err := client.createAd(123, "hello", "world")
	assert.NoError(t, err)
	assert.Equal(t, resp.Data.ID, int64(0))

	resp, err = client.createAd(123, "hello", "world")
	assert.NoError(t, err)
	assert.Equal(t, resp.Data.ID, int64(1))

	resp, err = client.createAd(123, "hello", "world")
	assert.NoError(t, err)
	assert.Equal(t, resp.Data.ID, int64(2))
}

func TestCreateUserWithEmptyNicknameOrEmail(t *testing.T) {
	client := getTestClient()

	_, err := client.createUser(123, "title", "")
	assert.ErrorIs(t, err, ErrBadRequest)

	_, err = client.createUser(125, "", "title")
	assert.ErrorIs(t, err, ErrBadRequest)

	_, err = client.createUser(128, "", "")
	assert.ErrorIs(t, err, ErrBadRequest)
}

func TestUpdateUserWithEmptyNicknameOrEmail(t *testing.T) {
	client := getTestClient()

	_, err := client.createUser(123, "hello", "world")
	assert.NoError(t, err)

	_, err = client.updateUser(123, "", "new_world")
	assert.ErrorIs(t, err, ErrBadRequest)

	_, err = client.updateUser(123, "new_world", "")
	assert.ErrorIs(t, err, ErrBadRequest)

	_, err = client.updateUser(123, "", "")
	assert.ErrorIs(t, err, ErrBadRequest)
}

func TestCreateUserWithTooLongNicknameOrEmail(t *testing.T) {
	client := getTestClient()

	nickname := strings.Repeat("a", 105)
	email := strings.Repeat("a", 505)

	_, err := client.createUser(123, nickname, "world")
	assert.ErrorIs(t, err, ErrBadRequest)

	_, err = client.createUser(123, "title", email)
	assert.ErrorIs(t, err, ErrBadRequest)

	_, err = client.createUser(123, nickname, email)
	assert.ErrorIs(t, err, ErrBadRequest)
}

func TestUpdateUserWithTooLongNicknameOrEmail(t *testing.T) {
	client := getTestClient()

	_, err := client.createUser(123, "hello", "world")
	assert.NoError(t, err)

	nickname := strings.Repeat("a", 101)
	email := strings.Repeat("a", 501)

	_, err = client.updateUser(123, nickname, "world")
	assert.ErrorIs(t, err, ErrBadRequest)

	_, err = client.updateUser(123, "title", email)
	assert.ErrorIs(t, err, ErrBadRequest)

	_, err = client.updateUser(123, nickname, email)
	assert.ErrorIs(t, err, ErrBadRequest)
}

func TestUpdateUserOfAnotherID(t *testing.T) {
	client := getTestClient()

	_, err := client.createUser(123, "hello", "world")
	assert.NoError(t, err)

	_, err = client.updateUser(100, "title", "text")
	assert.ErrorIs(t, err, ErrForbidden)
}

func TestGetUserOfAnotherID(t *testing.T) {
	client := getTestClient()

	_, err := client.createUser(123, "hello", "world")
	assert.NoError(t, err)

	_, err = client.getUserByID(100)
	assert.ErrorIs(t, err, ErrForbidden)
}

func TestGetAdOfAnotherID(t *testing.T) {
	client := getTestClient()

	_, err := client.createAd(123, "hello", "world")
	assert.NoError(t, err)

	_, err = client.getAdByID(15)
	assert.ErrorIs(t, err, ErrForbidden)
}

func TestCreateAd_EmptyTitle(t *testing.T) {
	client := getTestClient()

	_, err := client.createAd(123, "", "world")
	assert.ErrorIs(t, err, ErrBadRequest)
}

func TestCreateAd_TooLongTitle(t *testing.T) {
	client := getTestClient()

	title := strings.Repeat("a", 101)

	_, err := client.createAd(123, title, "world")
	assert.ErrorIs(t, err, ErrBadRequest)
}

func TestCreateAd_EmptyText(t *testing.T) {
	client := getTestClient()

	_, err := client.createAd(123, "title", "")
	assert.ErrorIs(t, err, ErrBadRequest)
}

func TestCreateAd_TooLongText(t *testing.T) {
	client := getTestClient()

	text := strings.Repeat("a", 501)

	_, err := client.createAd(123, "title", text)
	assert.ErrorIs(t, err, ErrBadRequest)
}

func TestUpdateAd_EmptyTitle(t *testing.T) {
	client := getTestClient()

	resp, err := client.createAd(123, "hello", "world")
	assert.NoError(t, err)

	_, err = client.updateAd(123, resp.Data.ID, "", "new_world")
	assert.ErrorIs(t, err, ErrBadRequest)
}

func TestUpdateAd_TooLongTitle(t *testing.T) {
	client := getTestClient()

	resp, err := client.createAd(123, "hello", "world")
	assert.NoError(t, err)

	title := strings.Repeat("a", 101)

	_, err = client.updateAd(123, resp.Data.ID, title, "world")
	assert.ErrorIs(t, err, ErrBadRequest)
}

func TestUpdateAd_EmptyText(t *testing.T) {
	client := getTestClient()

	resp, err := client.createAd(123, "hello", "world")
	assert.NoError(t, err)

	_, err = client.updateAd(123, resp.Data.ID, "title", "")
	assert.ErrorIs(t, err, ErrBadRequest)
}

func TestUpdateAd_TooLongText(t *testing.T) {
	client := getTestClient()

	text := strings.Repeat("a", 501)

	resp, err := client.createAd(123, "hello", "world")
	assert.NoError(t, err)

	_, err = client.updateAd(123, resp.Data.ID, "title", text)
	assert.ErrorIs(t, err, ErrBadRequest)
}
