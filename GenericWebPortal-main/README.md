<h1 align="center">GenericWebPortal</h1>
A generic web portal to access records from systematic mapping studies

## How to's

### Running the server through the command line

```commandline
python manage.py runserver
```

### How to prefill the database

The command below pre-populates the database the data contained within the file, provided in the placeholder 
``<fixturename>``. Execute the command from the content-root.
```commandline
python manage.py loaddata <fixturename>
```

In the case of the Modeling Tool Survey:
```commandline
python manage.py loaddata all_data.json
```

# TODO's ✅

## Overall
* Change font

## Data
* Create script that generates the fixture containing all the modeling tool data
* Prefill the database with data

## Header
* Remove the hover animation over the pencil icon within the "Suggest modeling tool edit"-button
* Adapt the "suggest new modeling tool" and "edit modeling" to phone and web mode

## Main body
* Add a container (margins/padding)
* Fix image example CSS

### Table
* Add option to hide columns