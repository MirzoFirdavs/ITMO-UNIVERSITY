@urllist = ();
@sitelist = ();
$input = "";
$is_empty = 667;
while(<>) {
    $input = $input.$_;
}
while ($input =~ /<\s*a.*?\bhref\s*=\s*"(\s*([^\"]*)\s*)".*?>/i) {
    push(@urllist, $1);
    $input =~ s/<\s*a.*?\bhref\s*=\s*(\"\s*([^\"]*)\s*\").*?>//i;
}
        
foreach (@urllist) {
    /(?<sc>([^\/:?#]+:)?\/\/)?(?<user>(\w+(:\w+)?@))?(?<hostname>[^:\/?#]+)(?<portname>\:\d)?([:\/?#].*)?/;
    $sc = $+{sc};
    $user = $+{user};
    $hostname = $+{hostname};
    $portname = $+{portname};
    if(!($sc =~ /^\s*$/)) {
        push(@sitelist, $hostname);
    } elsif (!($portname =~ /^\s*$/)) {
        push(@sitelist, $hostname);
    } else {
        $is_empty = 0;
    }
}
       
$prevURL = " ";
foreach (sort(@sitelist)) {
    if ($prevURL ne $_) {
        if (!($_ =~ /^\s*$/)) {
            print $_;
            print "\n";
        }
    }
    $prevURL = $_;
}