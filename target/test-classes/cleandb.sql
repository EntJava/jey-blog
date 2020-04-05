DELETE  FROM Post;

 INSERT INTO Post(post_id,title,author , category,publication_date,description,created_at) VALUES
(1,'How to create a generic Dao','James','Education',Now(),'Hibernate ORM is concerned with helping your application to achieve persistence.',Now()),
(2,'Hope','Henry','Education',Now(), 'So what is persistence? Persistence simply means that we would like our application’s data to outlive the applications process.',Now()),
(3,'Peace','Chantal','Politics',Now(),' In Java terms, we would like the state of (some of) our objects to live beyond the scope of the JVM so that the same state is available later.',Now()),
(4,'The happiness','Nadine','Social',Now(),'So what is persistence?',Now()),
(5,'Great Dane','Ella','Politics',Now(),'Hibernate ORM is concerned with helping your application to achieve persistence.',Now());
