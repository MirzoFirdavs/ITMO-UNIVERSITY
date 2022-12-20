#!/bin/sh

#1
cd $HOME
mkdir test

# 2
numberOfDirectories=0
numberOfHiddenFiles=0

for str in $(ls -a /etc)
do
	if [[ -d /etc/$str ]]; then
		echo "$str is directory"
		let numberOfDirectories=$numberOfDirectories+1
		
	elif [[ -f /etc/$str ]]; then
		echo "$str is file"
		if [[ $str = .* ]]; then
			let numberOfHiddenFiles=$numberOfHiddenFiles+1
		fi
	else
		echo "Nothing"
	fi

done > $HOME/test/list

# 3
echo $numberOfDirectories >> $HOME/test/list
echo $numberOfHiddenFiles >> $HOME/test/list

# 4
mkdir $HOME/test/links

# 5
ln $HOME/test/list $HOME/test/links/list_hlink

# 6
ln -s $HOME/test/list $HOME/test/links/list_slink

# 7
echo "Number of hard links to list_hlink"
ls -l $HOME/test/links/list_hlink | awk '{ print $2 }'
echo "Number of hard links to list"
ls -l $HOME/test/list | awk '{ print $2 }'
echo "Number of hard links to list_slink"
ls -l $HOME/test/links/list_slink | awk '{ print $2 }'

# 8
wc $HOME/test/list | awk '{ print $1 }' >> $HOME/test/links/list_hlink

# 9
cmp -s $HOME/test/links/list_hlink $HOME/test/links/list_slink && echo YES

# 10
mv $HOME/test/list $HOME/test/list1

# 11
cmp -s $HOME/test/links/list_hlink $HOME/test/links/list_slink && echo YES

# 12
#hard link not allowed for directory
ln $HOME/test/links $HOME/links_hlink

# 13
find /etc/ -name "*.conf" -type f > $HOME/list_conf

# 14
find /etc/ -maxdepth 1 -name "*.d" -type d > $HOME/list_d

# 15
cat $HOME/list_conf $HOME/list_d > $HOME/list_conf_d

# 16
mkdir $HOME/test/.sub

# 17
cp $HOME/list_conf_d $HOME/test/.sub/

# 18
cp -b $HOME/list_conf_d $HOME/test/.sub/

# 19
find $HOME/test/ -name "*"

# 20
man man > $HOME/man.txt

# 21
split -b 1024 $HOME/man.txt "man.txt_copy_"

# 22
mkdir $HOME/test/man.dir

# 23
mv man.txt_copy_* $HOME/test/man.dir

# 24
cat $HOME/test/man.dir/man.txt_copy_* > $HOME/test/man.dir/man.txt
rm $HOME/test/man.dir/man.txt_copy_*

# 25
cmp -s $HOME/man.txt $HOME/test/man.dir/man.txt && echo YES

# 26
sed -i '1s/^/Hello World! /' $HOME/man.txt
echo "Goodbye!" >> $HOME/man.txt

# 27
diff -u $HOME/man.txt $HOME/test/man.dir/man.txt > $HOME/man_difference

# 28
mv $HOME/man_difference $HOME/test/man.dir

# 29
patch $HOME/test/man.dir/man.txt < $HOME/test/man.dir/man_difference

# 30
cmp -s $HOME/man.txt $HOME/test/man.dir/man.txt && echo YES