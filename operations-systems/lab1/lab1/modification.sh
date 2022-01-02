#!/bin/bash
touch res.sh

dir="test.sh"

binBash=$(grep -o '#!/bin/bash$' $dir | cut -f 1)

echo "$binBash" > res.sh
grep -o '^[^#]*' $dir >> res.sh



