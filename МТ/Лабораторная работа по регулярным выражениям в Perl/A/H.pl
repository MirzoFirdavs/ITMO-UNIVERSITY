my $expr;
$expr = '\([^()]*\w+[^)(]*\)';

while (<>) {
	print if /$expr/;
}