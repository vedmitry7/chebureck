package com.vedmitryapps;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessagesRepository extends CrudRepository<Message, Long> {

   List<Message> findByTag(String tag);

}
