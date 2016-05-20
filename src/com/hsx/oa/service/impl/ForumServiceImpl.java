package com.hsx.oa.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsx.oa.base.BaseSupportImpl;
import com.hsx.oa.domain.Forum;
import com.hsx.oa.service.ForumService;

@Transactional
@Service
public class ForumServiceImpl extends BaseSupportImpl<Forum> implements ForumService {

}
