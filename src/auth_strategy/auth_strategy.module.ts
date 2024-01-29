import { Module } from '@nestjs/common';
import { AuthStrategyService } from './auth_strategy.service';
import { AuthStrategyController } from './auth_strategy.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { AuthStrategy } from './entities/auth_strategy.entity';

@Module({
  controllers: [AuthStrategyController],
  providers: [AuthStrategyService],
  imports:[
    TypeOrmModule.forFeature([
      AuthStrategy
    ])
  ]
})
export class AuthStrategyModule {}
