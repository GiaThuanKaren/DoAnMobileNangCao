import { Module } from '@nestjs/common';
import { UsersController } from './controllers/users/users.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { UsersService } from './service/users/users.service';
import { User } from './entites/user.entity';
import { AuthStrategy } from '../auth_strategy/entities/auth_strategy.entity';


@Module({
 

  controllers: [UsersController],
 
  imports:[
    TypeOrmModule.forFeature([
      User,
      AuthStrategy
    ])
  ],
  providers: [ UsersService]
})
export class UsersModule {}
