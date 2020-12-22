post		/registrer           //注册

//需要认证

get   		/user 				//个人信息

get  		 /user/posts		//获得当前用户的所有帖子

get  		 /user/commets	//所有参与过的评论



//public

get    		/users/{username}/posts  //获取某个用户所有的帖子

get 			/users/{username}/comments //获取某个用户参与的评论

get 			/categories/{category}/posts //获取某个板块的帖子

get 		/categories/{category}/posts/{postId}/comments/ 

get   		/categories/{category}/posts/{postId}/likes/



//已认证的用户或版主

post  		/categories/{category}/posts   //发布帖子到某个板块

delete 	 /categories/{category}/posts/{postId} //删除某个板块的帖子

patch       /categories/{category}/posts/{postId}//更新某个板块的帖子内容

post 		/categories/{category}/posts/{postId}/comments/  //评论某个帖子

post   		/categories/{category}/posts/{postId}/likes/  //点赞某个帖子













