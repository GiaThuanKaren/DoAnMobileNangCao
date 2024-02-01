import { Module } from '@nestjs/common';
import { UserPostService } from './user_post.service';
import { UserPostController } from './user_post.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { UserPost } from './entities/user_post.entity';
import { UserPermission } from '../user_permission/entities/user_permission.entity';

@Module({
  controllers: [UserPostController],
  providers: [UserPostService],
  imports: [
    TypeOrmModule.forFeature([
      UserPost,
      UserPermission
    ])
  ]
})
export class UserPostModule { }
