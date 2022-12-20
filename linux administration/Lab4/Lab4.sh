#!/bin/bash

# 1. Установите из сетевого репозитория пакеты, входящие в группу «Developments Tools»
dnf groupinstall "Development tools"

# 2. Установите из исходных кодов, приложенных к методическим указаниям пакет bastet-0.43. Для этого
# необходимо создать отдельный каталог и скопировать в него исходные коды проекта bastet. Вы
# можете использовать подключение сетевого каталога в хостовой операционной системе для передачи
# архива с исходными кодами в виртуальную машину. Далее следует распаковать архив до появления
# каталога с исходными файлами (в каталоге должен отображаться Makefile). После этого соберите
# пакет bastet и запустите его, чтобы удостовериться, что он правильно собрался. Затем модифицируйте
# Makefile, добавив в него раздел install. Обеспечьте при установке копирование исполняемого файла в
# /usr/bin с установкой соответствующих прав доступа. Выполните установку и проверьте, что любой
# пользователь может запустить установленный пакет.
tar zxvf bastet-0.43.tgz
cd bastet-0.43
yum install boost-devel
yum install ncurses-devel
make

echo -e "install:\n\tinstall ./bastet /usr/bin" >> Makefile
make install

# 3. Создайте файл task3.log, в который выведите список всех установленных пакетов.
dnf list installed >task3.log

# 4. Создайте файл task4_1.log, в который выведите список всех пакетов (зависимостей), необходимых
# для установки и работы компилятора gcc. Создайте файл task4_2.log, в который выведите список
# всех пакетов (зависимостей), установка которых требует установленного пакета libgcc.
dnf deplist gcc >task4_1.log
yum provides "*libgcc" >task4_2.log

# 5. Создайте каталог localrepo в домашнем каталоге пользователя root и скопируйте в него пакет
# checkinstall-1.6.2-3.el6.1.x86_64.rpm , приложенный к методическим указаниям. Создайте
# собственный локальный репозиторий с именем localrepo из получившегося каталога с пакетом.
yum install createrepo
mkdir localrepo
cd localrepo
cp /mnt/share/checkinstall-1.6.2-3.el6.1.x86_64.rpm ~/localrepo/checkinstall-1.6.2-3.el6.1.x86_64.rpm
createrepo ~/localrepo
cd /etc/yum.repos.d
[local]
name=localrepo
baseurl=file:/root/localrepo
enabled=1
gpgcheck=0

# 6. Создайте файл task6.log, в который выведите список всех доступных репозиториев.
dnf repolist enabled >task6.log

# 7. Настройте систему на работу только с созданным локальным репозиторием. 
# Выведите на экран список доступных для установки пакетов и убедитесь, 
# что доступен только один пакет, находящийся в локальном репозитории. Установите этот пакет.
mkdir /etc/yum.repos.d/repos
mv /etc/yum.repos.d/* /etc/yum.repos.d/repos
yum list avaible
dnf install ~/localrepo/checkinstall-1.6.2-3.el6.1.x86_64.rpm

# 8. Скопируйте в домашний каталог пакет fortunes-ru_1.52-2_all, приложенный к методическим
# рекомендациям, преобразуйте его в rpm пакет и установите.
alien --to-rpm fortunes-ru_1.52-2_all.deb
sudo rpm -i fortunes-ru-1.52-3.noarch.rpm

# 9. Скачайте из сетевого репозитория пакет nano. Пересоберите пакет таким образом, чтобы после его
# установки менеджером пакетов, появлялась возможность запустить редактор nano из любого каталога, введя команду newnano.
dnf download nano
dnf install https://extras.getpagespeed.com/release-el8-latest.rpm
dnf install rpmrebuild
rpmrebuild -enp nano-2.9.8-1.el8.x86_64.rpm
cd ~/rpmbuild/RPMS/x86_64/
yum remove nano
rpm -i newnano-2.9.8-1.el8.x86_64.rpm

# 10. Предъявите преподавателю изменения в системе и файл с описанием использованных команд.