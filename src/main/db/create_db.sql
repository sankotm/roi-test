create table fb_user
(
  id varchar(36) not null
    constraint user_pk
      primary key,
  name text,
  gender text,
  profile_picture_url text
);

alter table fb_user owner to twbrds_admin;

create unique index user_id_uindex
  on fb_user (id);

create table photo
(
  id varchar(36) not null
    constraint photo_pk
      primary key,
  user_id varchar(36)
    constraint fk_photo_user
      references fb_user
      on update cascade on delete cascade,
  fb_url text,
  image_url text,
  album_name text,
  reactions_summary text
);

alter table photo owner to twbrds_admin;

create unique index photo_id_uindex
  on photo (id);

