package com.lw.creatcodeapp.controller;

import com.lw.creatcodeapp.service.UserService;
import com.mybatisflex.core.paginate.Page;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.lw.creatcodeapp.model.entity.App;
import com.lw.creatcodeapp.service.AppService;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 应用 控制层。
 *
 * @author <a href="https://github.com/9331x-ZZzz">9331x</a>
 */

