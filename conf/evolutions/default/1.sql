# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table inventory_item (
  name                      varchar(255) not null,
  price                     double,
  quantity                  integer,
  img_path                  varchar(255),
  constraint pk_inventory_item primary key (name))
;

create sequence inventory_item_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists inventory_item;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists inventory_item_seq;

