package main

import (
	"errors"
	"flag"
	"fmt"
	"io"
	"os"
	"strings"
)

type Options struct {
	From      string
	To        string
	Offset    int
	Limit     int
	BlockSize int
	Conv      container
}

type container []string

func (c *container) String() string {
	return fmt.Sprint(*c)
}

func (c *container) Set(s string) error {
	for _, con := range strings.Split(s, ",") {
		*c = append(*c, con)
	}
	return nil
}

func ParseFlags() (*Options, error) {
	var opts Options

	flag.StringVar(&opts.From, "from", "", "file to read. by default - stdin")
	flag.StringVar(&opts.To, "to", "", "file to write. by default - stdout")
	flag.IntVar(&opts.Offset, "offset", 0, "the number of bytes inside input's that must be skipped when copying")
	flag.IntVar(&opts.Limit, "limit", 0, "maximum number of bytes to read")
	flag.IntVar(&opts.BlockSize, "block-size", 10, "the size of one block in bytes when reading and writing")
	flag.Var(&opts.Conv, "conv", "one or more of the possible transformations over the text, separated by commas.")

	flag.Parse()

	if opts.Offset < 0 {
		return &Options{}, fmt.Errorf("invalid offset, expected: offset > 0, actual: %d", opts.Offset)
	}

	flagUpperCase := false
	flagLowerCase := false

	for i := 0; i < len(opts.Conv); i++ {
		if opts.Conv[i] == "upper_case" {
			flagUpperCase = true
		}

		if opts.Conv[i] == "lower_case" {
			flagLowerCase = true
		}

		if opts.Conv[i] != "lower_case" && opts.Conv[i] != "upper_case" && opts.Conv[i] != "trim_spaces" {
			return &Options{}, fmt.Errorf(
				"invalid conversion type, expected: `lower_case`, `upper_case` or `trim_spaces`, actual: %s",
				opts.Conv[i],
			)
		}
	}

	if flagUpperCase && flagLowerCase {
		return &Options{}, errors.New("`upper_case` and `lower_case` at the same time")
	}

	return &opts, nil
}

func (opts *Options) inputReadCloser() (io.ReadCloser, error) {
	if opts.From == "" {
		return os.Stdin, nil
	}

	file, err := os.Open(opts.From)
	if err != nil {
		return nil, fmt.Errorf("can not open input file: %w", err)
	}

	return file, nil
}

func (opts *Options) outputWriteCloser() (io.WriteCloser, error) {
	if opts.To == "" {
		return os.Stdout, nil
	}

	if _, err := os.Stat(opts.To); err == nil {
		return nil, errors.New("output file already exists")
	}

	file, err := os.Create(opts.To)
	if err != nil {
		return nil, fmt.Errorf("can not open output file: %w", err)
	}

	return file, nil
}

func (opts *Options) readInput() (string, error) {
	input, err := opts.inputReadCloser()
	if err != nil {
		return "", fmt.Errorf("invalid input: %w", err)
	}
	defer input.Close()

	var data []byte
	if opts.Limit == 0 {
		data, err = io.ReadAll(input)
		if err != nil {
			return "", fmt.Errorf("unable to read from input source: %v", err)
		}
		if len(data) < opts.Offset {
			return "", errors.New("invalid offset, offset is greater than input size")
		}
	} else {
		data = make([]byte, opts.Offset+opts.Limit)
		n, err := io.ReadFull(input, data)
		if err != nil {
			return "", fmt.Errorf("unable to read from input source: %v", err)
		}
		if n < opts.Offset {
			return "", errors.New("offset is bigger than length")
		}
	}

	data = data[opts.Offset:]

	return string(data), nil
}

func (opts *Options) writeOutput(data string) error {
	writer, err := opts.outputWriteCloser()
	if err != nil {
		return fmt.Errorf("cannot create output file %w", err)
	}

	defer writer.Close()

	_, err = writer.Write([]byte(data))

	if err != nil {
		return fmt.Errorf("cannot write datas to file %w", err)
	}

	return nil
}

func generateConv(data string, convFlags container) (string, error) {
	for _, c := range convFlags {
		switch c {
		case "upper_case":
			data = strings.ToUpper(data)
		case "lower_case":
			data = strings.ToLower(data)
		case "trim_spaces":
			data = strings.TrimSpace(data)
		}
	}

	return data, nil
}

func main() {
	opts, err := ParseFlags()
	if err != nil {
		_, _ = fmt.Fprintf(os.Stderr, "can not parse flags: %v", err)
		os.Exit(1)
	}

	data, err := opts.readInput()
	if err != nil {
		_, _ = fmt.Fprintf(os.Stderr, "cannot read the data: %v", err)
		os.Exit(1)
	}

	newData, err := generateConv(data, opts.Conv)
	if err != nil {
		_, _ = fmt.Fprintf(os.Stderr, "cannot generate the data: %v", err)
		os.Exit(1)
	}

	if err := opts.writeOutput(newData); err != nil {
		_, _ = fmt.Fprintf(os.Stderr, "unable to write output: %v", err)
		os.Exit(1)
	}
}
