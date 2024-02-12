import { Test, TestingModule } from '@nestjs/testing';
import { AuthStrategyController } from './auth_strategy.controller';
import { AuthStrategyService } from './auth_strategy.service';

describe('AuthStrategyController', () => {
  let controller: AuthStrategyController;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [AuthStrategyController],
      providers: [AuthStrategyService],
    }).compile();

    controller = module.get<AuthStrategyController>(AuthStrategyController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
});
