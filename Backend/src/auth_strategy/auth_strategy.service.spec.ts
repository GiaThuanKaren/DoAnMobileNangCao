import { Test, TestingModule } from '@nestjs/testing';
import { AuthStrategyService } from './auth_strategy.service';

describe('AuthStrategyService', () => {
  let service: AuthStrategyService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [AuthStrategyService],
    }).compile();

    service = module.get<AuthStrategyService>(AuthStrategyService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
