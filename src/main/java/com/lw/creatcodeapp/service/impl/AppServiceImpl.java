package com.lw.creatcodeapp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.lw.creatcodeapp.constant.AppConstant;
import com.lw.creatcodeapp.core.AiCodeGeneratorFacade;
import com.lw.creatcodeapp.core.builder.VueProjectBuilder;
import com.lw.creatcodeapp.exception.BusinessException;
import com.lw.creatcodeapp.exception.ErrorCode;
import com.lw.creatcodeapp.exception.ThrowUtils;
import com.lw.creatcodeapp.model.dto.app.AppAddRequest;
import com.lw.creatcodeapp.model.dto.app.AppQueryRequest;
import com.lw.creatcodeapp.model.entity.User;
import com.lw.creatcodeapp.model.enums.CodeGenTypeEnum;
import com.lw.creatcodeapp.model.vo.AppVO;
import com.lw.creatcodeapp.model.vo.UserVO;
import com.lw.creatcodeapp.service.UserService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.lw.creatcodeapp.model.entity.App;
import com.lw.creatcodeapp.mapper.AppMapper;
import com.lw.creatcodeapp.service.AppService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 应用 服务层实现。
 *
 * @author <a href="https://github.com/9331x-ZZzz">9331x</a>
 */
//@Service
//public class AppServiceImpl extends ServiceImpl<AppMapper, App>  implements AppService{
//
//}
