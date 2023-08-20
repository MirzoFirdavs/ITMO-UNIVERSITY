while (<>) {
	print if /(\o{172}).{3}(\o{172})/
}