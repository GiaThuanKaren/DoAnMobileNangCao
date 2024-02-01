
import { Injectable } from '@nestjs/common';
import { CreateAuthStrategyDto } from './dto/create-auth_strategy.dto';
import { UpdateAuthStrategyDto } from './dto/update-auth_strategy.dto';
import { Repository } from 'typeorm';
import { InjectRepository } from '@nestjs/typeorm';
import { AuthStrategy } from './entities/auth_strategy.entity';

@Injectable()
export class AuthStrategyService {
  constructor(@InjectRepository(AuthStrategy) private AuthStrategyRepository: Repository<AuthStrategy>) {

  }
  create(createAuthStrategyDto: CreateAuthStrategyDto) {
    const newAuthStrategy = this.AuthStrategyRepository.create({
      ...createAuthStrategyDto
    })

    return this.AuthStrategyRepository.save(newAuthStrategy);
  }

  findAll() {
    return this.AuthStrategyRepository.find();
  }

  findOne(id: number) {
    return this.AuthStrategyRepository.findOne({
      where: {
        id: id
      }
    });
  }

  update(id: number, updateAuthStrategyDto: UpdateAuthStrategyDto) {

    return this.AuthStrategyRepository.update({
      id: id
    }, {
      ...updateAuthStrategyDto
    });
  }

  remove(id: number) {

    return this.AuthStrategyRepository.delete({
      id
    });
  }
}
