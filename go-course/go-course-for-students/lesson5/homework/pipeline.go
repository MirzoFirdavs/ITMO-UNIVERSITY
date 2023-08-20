package executor

import (
	"context"
)

type (
	In  <-chan any
	Out = In
)

type Stage func(in In) (out Out)

func ExecutePipeline(ctx context.Context, in In, stages ...Stage) Out {
	if in == nil {
		ch := make(chan any)
		defer close(ch)

		return ch
	}

	for i := 0; i < len(stages); i++ {
		jobStream := make(chan any)

		go func(in In) {
			defer close(jobStream)

			for {
				select {
				case <-ctx.Done():
					return
				case val, ok := <-in:
					if !ok {
						return
					}

					jobStream <- val
				}
			}
		}(in)

		in = stages[i](jobStream)
	}

	return in
}
