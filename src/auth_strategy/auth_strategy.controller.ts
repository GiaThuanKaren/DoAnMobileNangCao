import { Controller, Get, Post, Body, Patch, Param, Delete, Put } from '@nestjs/common';
import { AuthStrategyService } from './auth_strategy.service';
import { CreateAuthStrategyDto } from './dto/create-auth_strategy.dto';
import { UpdateAuthStrategyDto } from './dto/update-auth_strategy.dto';
import { ApiTags } from '@nestjs/swagger';

@Controller('auth-strategy')
@ApiTags("Auth Strategy")
export class AuthStrategyController {
  constructor(private readonly authStrategyService: AuthStrategyService) {}
  @Get()
  findAll() {
    return this.authStrategyService.findAll();
  }
  @Post()
  async create(@Body() createAuthStrategyDto: CreateAuthStrategyDto) {
    return await this.authStrategyService.create(createAuthStrategyDto);
  }

  @Get(':id')
  findOne(@Param('id') id: string) {
    return this.authStrategyService.findOne(+id);
  }

  @Put(':id')
  update(@Param('id') id: string, @Body() updateAuthStrategyDto: UpdateAuthStrategyDto) {
    return this.authStrategyService.update(+id, updateAuthStrategyDto);
  }

  @Delete(':id')
  remove(@Param('id') id: string) {
    return this.authStrategyService.remove(+id);
  }
}
