while (<>) {
	s/\([^\)]+\)/()/g;
	print;
}
