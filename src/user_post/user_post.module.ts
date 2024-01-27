import { Module } from '@nestjs/common';
import { UserPostService } from './user_post.service';
import { UserPostController } from './user_post.controller';

@Module({
  controllers: [UserPostController],
  providers: [UserPostService]
})
export class UserPostModule {}
