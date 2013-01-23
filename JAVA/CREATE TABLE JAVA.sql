DROP Table files;
CREATE Table files
(
	id serial not null UNIQUE,
	path text not null UNIQUE,
	hash char(32),
	size bigint,
	fid int,
	atitle text,
	eptitle text,
	type text,
	epn int	
)