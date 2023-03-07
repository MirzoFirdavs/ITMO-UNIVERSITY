while (<>) {
	s/(\w)(\1)/$2/g;
	print;
}