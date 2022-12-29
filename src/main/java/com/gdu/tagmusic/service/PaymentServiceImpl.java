package com.gdu.tagmusic.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.tagmusic.domain.UserDTO;
import com.gdu.tagmusic.mapper.PaymentMapper;
import com.gdu.tagmusic.util.PageUtil;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentMapper paymentMapper;

	@Autowired
	private PageUtil pageUtil;

	@Override
	public Map<String, Object> getPassList() {

		Map<String, Object> map = new HashMap<>();
		map.put("passList", paymentMapper.selectPass());

		return map;
	}

	@Transactional
	@Override
	public Map<String, Object> buyPass(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String email = request.getParameter("email");
		String price = request.getParameter("price");
		String passNo = request.getParameter("passNo");
		String payPg = request.getParameter("payPg");
		String ticketName = request.getParameter("ticketName");
		String merchantUid = request.getParameter("merchantUid");

		map.put("email", email);
		map.put("price", price);
		map.put("passNo", passNo);
		map.put("payPg", payPg);
		map.put("ticketName", ticketName);
		map.put("merchantUid", merchantUid);

		int paymentCnt = paymentMapper.selectPaymentCnt(map);
		Map<String, Object> result = new HashMap<>();
		if (paymentCnt == 0) {
			int passResult = paymentMapper.insertPayment(map);
			int logResult = paymentMapper.insertPaymentLog(map);
			if (passResult > 0 && logResult > 0) {
				result.put("result", "success");
			} else {
				result.put("result", "false");
			}
		} else if (paymentCnt >= 1) {
			int extendResult = paymentMapper.updatePaymentExtend(map);
			int logResult = paymentMapper.insertPaymentLog(map);
			if (extendResult > 0 && logResult > 0) {
				result.put("result", "extension");
			} else {
				result.put("result", "false");
			}
		}
		return result;
	}

	@Transactional
	@Override
	public Map<String, Object> presentPass(HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>();
		String email = request.getParameter("email");
		String price = request.getParameter("price");
		String passNo = request.getParameter("passNo");
		String payPg = request.getParameter("payPg");
		String ticketName = request.getParameter("ticketName");
		String merchantUid = request.getParameter("merchantUid");
		String sessionEmail = request.getParameter("sessionEmail");

		map.put("email", email);
		map.put("price", price);
		map.put("passNo", passNo);
		map.put("payPg", payPg);
		map.put("ticketName", ticketName);
		map.put("merchantUid", merchantUid);
		map.put("sessionEmail", sessionEmail);

		int paymentCnt = paymentMapper.selectPaymentCnt(map);
		System.out.println(paymentCnt);
		Map<String, Object> result = new HashMap<>();
		if (paymentCnt == 0) {
			int passResult = paymentMapper.insertPayment(map);
			int logResult = paymentMapper.insertPaymentGiftLog(map);
			if (passResult > 0 && logResult > 0) {
				result.put("result", 1);
			} else {
				result.put("result", 0);
			}
		} else if (paymentCnt >= 1) {
			int extendResult = paymentMapper.updatePaymentExtend(map);
			int logResult = paymentMapper.insertPaymentGiftLog(map);
			if (extendResult > 0 && logResult > 0) {
				result.put("result", 1);
			} else {
				result.put("result", 0);
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> getRemainindperiod(HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<>();
			Optional<String> strOpt = Optional.ofNullable(request.getParameter("email"));
			String email = strOpt.orElse("1");
			map.put("email", email);
			Optional<Integer> opt = Optional.ofNullable(paymentMapper.selectRemainiend(map));
			int remaining = opt.orElse(0);
			Map<String, Object> result = new HashMap<>();
			if (email.equals("1") || remaining != 0) {
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date dDay = paymentMapper.selectPassDday(map);
				result.put("remainingDay", sdf1.format(dDay));
				result.put("dDay", remaining);
			}
			return result;
		} catch (Exception e) {
			Map<String, Object> map = new HashMap<>();
			map.put("error", "로그인 안된 유저 및 이용권 없는사람 방지용입니다.");
			e.printStackTrace();
			return map;
		}
	}

	@Override
	public Map<String, Object> selectRecipientByEmail(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		String email = request.getParameter("email");
		result.put("email", email);
		result.put("result", paymentMapper.selectRecipientByEmail(result));
		System.out.println(result.get("result"));
		return result;
	}

	@Override
	public Map<String, Object> getLogList(HttpServletRequest request) {
		String email = request.getParameter("email");
		int page = Integer.parseInt(request.getParameter("page"));
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		map.put("email", email);
		int logCount = paymentMapper.selectPaymentLogListCount(map);
		result.put("logCount", logCount);
		pageUtil.setPageUtil(page, 10, logCount);
		map.put("begin", pageUtil.getBegin() - 1);
		map.put("recordPerPage", pageUtil.getRecordPerPage());
		map.put("end", pageUtil.getEnd());

		result.put("logList", paymentMapper.selectPaymentLogList(map));
		result.put("pageUtil", pageUtil);

		return result;
	}

	@Override
	public Map<String, Object> removeLog(List<String> payLogNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("payLogNo", payLogNo);
		Map<String, Object> result = new HashMap<>();
		result.put("result", paymentMapper.deleteLogByNo(map));
		return result;
	}

	@Override
	public Map<String, Object> isHavePass(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			String email = ((UserDTO) session.getAttribute("loginUser")).getEmail();
			Map<String, Object> map = new HashMap<>();
			map.put("email", email);
			map.put("result", paymentMapper.selectIsPaymentCnt(map));
			return map;
		} catch (NullPointerException e) {
			Map<String, Object> map = new HashMap<>();
			map.put("isNotLogin", "loginPlz");
			return map;
		}

	}

	@Transactional
	@Override
	public Map<String, Object> couponUse(HttpServletRequest request) {
		// Optional<String> strOpt = Optional.ofNullable(request.getParameter("email"));
		// String email = strOpt.orElse(" ");
		String email = request.getParameter("email");
		System.out.println("이메일" + email);

		String couponCode = request.getParameter("couponCode");

		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		map.put("couponCode", couponCode);

		Optional<Integer> opt = Optional.ofNullable(paymentMapper.selectCouponByCode(map));
		int couponCount = opt.orElse(0);

		if (couponCount > 0) {
			int useOrNot = paymentMapper.selectCouponUseCnt(map);
			if (useOrNot == 0) {

				int updateResult = paymentMapper.updateCoupon(map);
				int insertResult = paymentMapper.insertCouponUse(map);
				if (updateResult == 1 && insertResult == 1) {
					// 등록 성공
					int paymentCnt = paymentMapper.selectPaymentCouponCnt(map);
					if (paymentCnt == 0) {
						int passResult = paymentMapper.insertPaymentCpn(map);
						
						if (passResult > 0) {
							map.put("result", "success");
						} else {
							map.put("result", "false");
						}
					} else if (paymentCnt >= 1) {
						int extendResult = paymentMapper.updatePaymentExtendCpn(map);
						
						if (extendResult > 0) {
							map.put("result", "extension");
						} else {
							map.put("result", "false");
						}
					}
				} else {
					// 등록 실패
					map.put("result", "false!"); 
				}

			} else {
				map.put("result", "duplication");
				// 쿠폰 중복
			}
		} else {
			map.put("result", "null");
			// 쿠폰 없음
		}
		return map;
	}

}
