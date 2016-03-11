# PlayChat
Chat web application written using Java and Play Framework

### Activator console
To start activator console application, run `activator` from root directory of the project.

### Compiling
In activator console type `compile`

### Running
In activator console type `run`

### Database
PlayChat uses MySQL database

You can import database and tables structure from file `playchat.sql` (placed in root directory)

To change MySQL connection parameters, edit `conf/application.conf` file. At the bottom of this file there are `default.url = "mysql://root:password@localhost/playchat"` which should be changed.