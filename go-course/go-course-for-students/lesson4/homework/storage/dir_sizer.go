package storage

import (
	"context"
	"fmt"

	"golang.org/x/sync/errgroup"
)

// Result represents the Size function result
type Result struct {
	// Total Size of File objects
	Size int64
	// Count is a count of File objects processed
	Count int64
}

type DirSizer interface {
	// Size calculate a size of given Dir, receive a ctx and the root Dir instance
	// will return Result or error if happened
	Size(ctx context.Context, d Dir) (Result, error)
}

// sizer implement the DirSizer interface
type sizer struct {
	// maxWorkersCount number of workers for asynchronous run
	maxWorkersCount int
}

// NewSizer returns new DirSizer instance
func NewSizer() DirSizer {
	return &sizer{
		maxWorkersCount: 5,
	}
}

// Size calculates size of the directory using no more than a.maxWorkersCount workers at a time
func (a *sizer) Size(ctx context.Context, d Dir) (Result, error) {
	res := Result{}

	dirs, files, err := d.Ls(ctx)
	if err != nil {
		return Result{}, fmt.Errorf("error while ls: %w", err)
	}

	for i := 0; i < len(files); i++ {
		var size, err = files[i].Stat(ctx)
		if err != nil {
			return Result{}, fmt.Errorf("error while Stat: %w", err)
		}

		res.Size += size
		res.Count++
	}

	group, ctx := errgroup.WithContext(ctx)
	limitCh := make(chan struct{}, a.maxWorkersCount)

	resultCh := make(chan Result, len(dirs))

	for i := 0; i < len(dirs); i++ {
		limitCh <- struct{}{}

		i := i

		group.Go(func() error {
			<-limitCh

			currentRes, err := a.Size(ctx, dirs[i])
			if err != nil {
				return fmt.Errorf("error on recursive call of Size: %w", err)
			}

			resultCh <- Result{
				Size:  currentRes.Size,
				Count: currentRes.Count,
			}

			return nil
		})
	}

	if err := group.Wait(); err != nil {
		return Result{}, err
	}

	close(resultCh)

	for currentRes := range resultCh {
		res.Size += currentRes.Size
		res.Count += currentRes.Count
	}

	return res, nil
}
