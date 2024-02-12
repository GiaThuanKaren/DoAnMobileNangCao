import { Module } from '@nestjs/common';
import { PostService } from './post.service';
import { PostController } from './post.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Post } from './entities/post.entity';
import { UserPost } from '../user_post/entities/user_post.entity';
import { UserPermission } from '../user_permission/entities/user_permission.entity';

@Module({
  controllers: [PostController],
  providers: [PostService],
  imports: [
    TypeOrmModule.forFeature([
      Post,
      UserPost,
      UserPermission
    ])
  ]
})
export class PostModule { }
