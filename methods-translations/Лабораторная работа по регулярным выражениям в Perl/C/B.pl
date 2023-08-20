$empty_string = -666;
$is_empty = 666;
 
while (<>) {
    s/<([^>]*)>//g;
    if (/[^\s]/) {
        $empty_string = 666;
    }
    if ($empty_string == 666) {
        s/^\s+|\s+$//g;
        s/(\s){2,}/ /g;
        if (/^\s*$/) {
            $is_empty = -666;
        } else {
            if ($is_empty == -666) {
                print "\n";
            }
            $is_empty = 666;
            print;
            print "\n"; 
        }
    }
}