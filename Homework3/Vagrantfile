# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/xenial64"

  config.vm.define "apache_host" do |apache_host|
  	  apache_host.vm.network "private_network", ip: "127.0.0.1"
  	  apache_host.vm.network "forwarded_port", guest: 80, host: 80
  	  apache_host.vm.provision "shell", inline: <<-SHELL
  	    apt-get install -y apache2
        apt install libapache2-mod-wsgi
        apache2ctl -t -D DUMP_MODULES
        mkdir /var/www/hello_world
        mkdir /var/www/hello_world/public_html
        mkdir /var/www/pyex
        mkdir /var/www/pyex/public_html
        touch /var/www/hello_world/public_html/index.html
        echo "<html>Hello world</html>" > /var/www/hello_world/public_html/index.html
        touch /etc/apache2/sites-available/hello.world.conf
        echo "<VirtualHost *:80>
        	ServerName hello.world
        	ServerAlias www.hello.world
        	ServerAdmin aad@localhost
        	DocumentRoot /var/www/hello_world/public_html
        	ErrorLog \${APACHE_LOG_DIR}/error.log
        	CustomLog \${APACHE_LOG_DIR}/access.log combined
        </VirtualHost>" > /etc/apache2/sites-available/hello.world.conf
        touch /var/www/pyex/public_html/ex.wsgi
        echo "\
import datetime
def application(environ, start_response): \n    status = '200 OK'
    output = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    response_headers = [('Content-type', 'text/html'),
                        ('Content-Length', str(len(output)))]
    start_response(status, response_headers)
    return [output]" > /var/www/pyex/public_html/ex.wsgi
        touch /etc/apache2/sites-available/ex.py.conf
        echo "\
        <VirtualHost *:80>
            DocumentRoot /var/www/pyex/public_html
            ServerName ex.py
            ServerAlias www.ex.py
            ErrorLog \${APACHE_LOG_DIR}/dev.trip.pl.error.log
            LogLevel warn
            CustomLog \${APACHE_LOG_DIR}/dev.trip.pl.access.log combined
            WSGIScriptAlias / /var/www/pyex/public_html/ex.wsgi
            <Directory /var/www/pyex/public_html>
                Options Indexes FollowSymLinks MultiViews
                AllowOverride All
                Require all granted
            </Directory>
        </VirtualHost>
        " > /etc/apache2/sites-available/ex.py.conf
        echo "
127.0.0.1 hello.world
127.0.0.1 www.hello.world
127.0.0.1 ex.py
127.0.0.1 www.ex.py" >> /etc/hosts
        a2ensite ex.py
        a2ensite hello.world
        systemctl reload apache2
  	  SHELL
    end
end