package tests

import (
	"bytes"
	"encoding/json"
	"fmt"
	"net/http"
)

type userData struct {
	Nickname string `json:"nickname"`
	Email    string `json:"email"`
	UserID   int64  `json:"user_id"`
}

type userResponse struct {
	Data userData `json:"data"`
}

func (tc *testClient) createUser(userID int64, nickname string, email string) (userResponse, error) {
	body := map[string]any{
		"nickname": nickname,
		"email":    email,
		"user_id":  userID,
	}

	data, err := json.Marshal(body)
	if err != nil {
		return userResponse{}, fmt.Errorf("unable to marshal: %w", err)
	}

	req, err := http.NewRequest(http.MethodPost, tc.baseURL+"/api/v1/user", bytes.NewReader(data))
	if err != nil {
		return userResponse{}, fmt.Errorf("unable to create request: %w", err)
	}

	req.Header.Add("Content-Type", "application/json")

	var response userResponse
	err = tc.getResponse(req, &response)
	if err != nil {
		return userResponse{}, err
	}

	return response, nil
}

func (tc *testClient) updateUser(userID int64, nickname string, email string) (userResponse, error) {
	body := map[string]any{
		"user_id":  userID,
		"nickname": nickname,
		"email":    email,
	}

	data, err := json.Marshal(body)
	if err != nil {
		return userResponse{}, fmt.Errorf("unable to marshal: %w", err)
	}

	req, err := http.NewRequest(http.MethodPut, fmt.Sprintf(tc.baseURL+"/api/v1/user/%d", userID), bytes.NewReader(data))
	if err != nil {
		return userResponse{}, fmt.Errorf("unable to create request: %w", err)
	}

	req.Header.Add("Content-Type", "application/json")

	var response userResponse
	err = tc.getResponse(req, &response)
	if err != nil {
		return userResponse{}, err
	}

	return response, nil
}

func (tc *testClient) getUserByID(userID int64) (userResponse, error) {
	req, err := http.NewRequest(http.MethodGet, fmt.Sprintf(tc.baseURL+"/api/v1/user/%d", userID), nil)
	if err != nil {
		return userResponse{}, fmt.Errorf("unable to create request: %w", err)
	}

	var response userResponse
	err = tc.getResponse(req, &response)
	if err != nil {
		return userResponse{}, err
	}

	return response, nil
}
